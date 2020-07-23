package com.zps.imchat.netty;

import com.zps.imchat.bean.ChatLogs;
import com.zps.imchat.bean.MyFriends;
import com.zps.imchat.jsonbean.MsgJson;
import com.zps.imchat.rabbitmq.MqServer;
import com.zps.imchat.service.ChatLogsService;
import com.zps.imchat.service.GroupUserService;
import com.zps.imchat.service.MyFzService;
import com.zps.imchat.utils.JsonUtil;
import com.zps.imchat.utils.RabbitmqUtil;
import com.zps.imchat.utils.SpringBeanUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;

/**
 * @author: zps
 * @desc : 消息核心处理类
 **/
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    RabbitmqUtil rabbitmqUtil = SpringBeanUtil.getBean(RabbitmqUtil.class);

    private static final Logger log = LoggerFactory.getLogger(WebSocketServerHandler.class);

    /**保存连接进来的用户的chanel */
    public static ChannelGroup userMap = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    MyFzService myFzService = SpringBeanUtil.getBean(MyFzService.class);

    ChatLogsService chatLogsService = SpringBeanUtil.getBean(ChatLogsService.class);

    GroupUserService groupUserService = SpringBeanUtil.getBean(GroupUserService.class);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                TextWebSocketFrame context) throws Exception {
         /**获取channnel */
         Channel channel = ctx.channel();
         /**获取发过来的消息 */
         String msg = context.text();

         System.out.println(msg);

         //channel.writeAndFlush(new TextWebSocketFrame("干嘛呢"));
         MsgJson msgData = JsonUtil.jsonTopojo(msg ,MsgJson.class);
         String type = msgData.getType();

         /**根据发送的类型不同进行不同的核心逻辑处理 */
         switch (type) {

             case "connect" :   /**第一次连接，将userId和channnel进行绑定 */
                 String userId = msgData.getDataMap().get("userid");
                 System.out.println(userId);
                 SessionMap.put(userId , channel);

                 //离线未读聊天信息获取
                 Object message = rabbitmqUtil.getMsgFromQueue(MqServer.QUEUE + userId);
                 if(message == null) break;
                 List<MsgJson> msgList = new ArrayList<>();

                 //获取所有离线消息
                 while (message != null){
                     msgList.add((MsgJson) message);
                     message = rabbitmqUtil.getMsgFromQueue(MqServer.QUEUE + userId);
                 }
                 channel.writeAndFlush(new TextWebSocketFrame(JsonUtil.pojoTojson(msgList)));
                 break;

             case "singleChat" :  /**单聊 */
                 /**获取接收者的id */
                 String toid = msgData.getDataMap().get("toid");
                 Channel toChannle = SessionMap.get(toid);

                 //保存聊天历史
                 ChatLogs chatLogs = new ChatLogs();
                 chatLogs.setFromid(Long.parseLong(msgData.getDataMap().get("fromid")));
                 chatLogs.setToid(Long.parseLong(toid));
                 chatLogs.setType("singleChat");
                 chatLogs.setContent(msgData.getDataMap().get("context"));
                 chatLogs.setSendtime(msgData.getSendtime());
                 chatLogs.setStatus(1);  //1 暂时默认已读

                 /** 保存到数据库 */
                 chatLogsService.saveLogs(chatLogs);

                 //发送消息
                 sendMessage(toid , msgData);
                 break;

             case "groupChat" :   //群聊

                 /**
                  * 相应的离线消息保存
                  */
                 String groupid = msgData.getDataMap().get("groupid");
                 ChatLogs groupChatLogs = new ChatLogs();
                 groupChatLogs.setToid(Long.parseLong(groupid));
                 groupChatLogs.setFromid(Long.parseLong(msgData.getDataMap().get("fromid")));
                 groupChatLogs.setContent(msgData.getDataMap().get("context"));
                 groupChatLogs.setSendtime(msgData.getSendtime());
                 groupChatLogs.setStatus(1);
                 groupChatLogs.setType("groupChat");
                 /** 保存到数据库 */
                 chatLogsService.saveLogs(groupChatLogs);

                 /** 获取群中群员列表*/
                 List<Long> groupUsersId = groupUserService.findGroupUsersId(Long.parseLong(groupid));

                 /** 循环遍历群员，进行消息的推送*/
                 for(Long id : groupUsersId) {
                     sendMessage(id.toString(), msgData);
                 }
                 break;

             case "addFriend" :   //好友申请

                 //获取申请好友id
                 String friendid = msgData.getDataMap().get("friendid");

                 /**保申请记录保存到数据库，好友状态设置为0 */
                 MyFriends myFriend = new MyFriends();
                 myFriend.setMyFzId(Long.parseLong(msgData.getDataMap().get("fzid")));
                 myFriend.setNickname(msgData.getDataMap().get("nickname"));
                 myFriend.setUserId(Long.parseLong(friendid));
                 myFriend.setStatu(0);
//                 保存到数据库，为实现
                 myFzService.addFriend(myFriend);

                 /** 修改消息类型为申请确认消息，然后推送给好友，*/
                 msgData.setType("checkAddFriend");
                 /**向好友推送消息 */
                 sendMessage(friendid , msgData);
                 break;

             case "agreeAddFriend" :  //同意加好友

                 MyFriends myFriend2 = new MyFriends();
                 myFriend2.setMyFzId(Long.parseLong(msgData.getDataMap().get("myfzid")));
                 myFriend2.setUserId(Long.parseLong(msgData.getDataMap().get("userid")));
                 myFriend2.setNickname(msgData.getDataMap().get("nickname"));
                 myFriend2.setStatu(1);
                 /** 保存到数据库*/
                 myFzService.addFriend(myFriend2);
                 /** 更改申请人好友状态*/
//                 myFzService.updateStaus(Long.parseLong(msgData.getDataMap().get("fzid")) ,
//                                         Long.parseLong(msgData.getDataMap().get("friendid")));
                 break;

             case "refuseAddFriend" :  //拒绝加好友，直接从数据库中删除好友记录
                 myFzService.deleteFriend(Long.parseLong(msgData.getDataMap().get("fzid")) ,
                                         Long.parseLong(msgData.getDataMap().get("userid")));
                 break;

             case "removeFriend" :  //删除好友
                 myFzService.deleteFriend(Long.parseLong(msgData.getDataMap().get("fzid")) ,
                         Long.parseLong(msgData.getDataMap().get("friendid")));

                 //向被删除的好友推送消息
                 MsgJson msgJson = new MsgJson();
                 msgJson.setType("friendOver");
                 Map<String , String> data = new HashMap<>();
                 data.put("fromid" , msgData.getDataMap().get("userid"));
                 String toid1 = msgData.getDataMap().get("friendid");
                 data.put("toid" , toid1);
                 data.put("context" , "你已不是对方好友");
                 msgJson.setDataMap(data);
                 msgJson.setExtand("IM聊天");
                 msgJson.setSendtime(new Date());

                 /** 推送消息*/
                 sendMessage(toid1 , msgJson);
                 break;

             case "addFriendToGroup" :  //群主主动拉人进群

                 //保存申请记录到数据库，staus默认为0
//               groupUserService.addGroupUser(Long.parseLong(msgData.getDataMap().get("groupid")) ,
//                                               Long.parseLong(msgData.getDataMap().get("friendid")));
                 //向好友发起申请同意
                 msgData.setType("checkAddGroupByUser");
                 //推送消息给好友
                 sendMessage(msgData.getDataMap().get("friendid") , msgData);
                 break;

             case "agreeAddGroupByUser" :  //好友或者群主同意加群

                 //更新群员状态
//                 groupUserService.updateGroupUser(Long.parseLong(msgData.getDataMap().get("groupid")) ,
//                                               Long.parseLong(msgData.getDataMap().get("friendid")));
                 break;

             case "refuseAddGroupByUser" :  //好友拒绝加群

                 //删除记录
//                 groupUserService.deleteGroupUser(Long.parseLong(msgData.getDataMap().get("groupid")) ,
//                                               Long.parseLong(msgData.getDataMap().get("friendid")));
                  break;

             case "addGroup" :      //好友主动加群
                 //保存申请记录到数据库，staus默认为0
//               groupUserService.addGroupUser(Long.parseLong(msgData.getDataMap().get("groupid")) ,
//                                               Long.parseLong(msgData.getDataMap().get("friendid")));
                 //向群主发起申请同意
                 msgData.setType("checkBygroupUser");
                 //推送消息给好友
                 sendMessage(msgData.getDataMap().get("groupUserId") , msgData);
                 break;

             case "removeGroupByGroupUser" :   //群主主动踢人
                 //删除记录
//                 groupUserService.deleteGroupUser(Long.parseLong(msgData.getDataMap().get("groupid")) ,
//                                               Long.parseLong(msgData.getDataMap().get("userid")));
                 List<Long> groupid1 = groupUserService.findGroupUsersId(Long.parseLong(msgData.getDataMap().get("groupid")));
                 msgData.setType("adviceToGroupUser");

                 //向群员广播消息
                 for(Long id : groupid1){
                     sendMessage(id.toString() , msgData);
                 }
                 break;

             case "removeGroupByUser" :
                 //删除记录
//                 groupUserService.deleteGroupUser(Long.parseLong(msgData.getDataMap().get("groupid")) ,
//                                               Long.parseLong(msgData.getDataMap().get("userid")));
                 List<Long> groupid2 = groupUserService.findGroupUsersId(Long.parseLong(msgData.getDataMap().get("groupid")));
                 msgData.setType("adviceToGroup");

                 //向群员广播消息
                 for(Long id : groupid2){
                     sendMessage(id.toString() , msgData);
                 }
                 break;
         }
    }

    /**
     * 发送消息方法封装
     * @param toid
     */
    private void sendMessage(String toid , MsgJson msgData) {
        Channel toChannle = SessionMap.get(toid);
        if(toChannle == null){    //如果为空，把该消息缓存到MQ消息队列中
            rabbitmqUtil.sendMessageToExchange(MqServer.IM_exchange ,
                    MqServer.QUEUE + toid , msgData);
        }else{

            //从userMap中获取最新的channel，防止用户的连接过期失效的情况
            toChannle = userMap.find(toChannle.id());
            if(toChannle == null){  //如果为空，把该消息缓存到MQ消息队列中
                rabbitmqUtil.sendMessageToExchange(MqServer.IM_exchange ,
                        MqServer.QUEUE + toid , msgData);

            }
            //用户在线，直接发送消息
            toChannle.writeAndFlush(new TextWebSocketFrame(JsonUtil.pojoTojson(msgData)));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    //新用户连接进来，添加到userMap中
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        userMap.add(channel);
        System.out.println("有新连接\" + \"当前连接 ：\" + userMap.size()");
        log.info("有新连接" + "当前连接 ：" + userMap.size() );

    }

    //移除用户连接
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
         userMap.remove(ctx.channel());
         String channelid = ctx.channel().id().asShortText();
         log.info("由用户退出：" + channelid);
    }
}
