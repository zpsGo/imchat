package com.zps.imchat.netty;

import com.zps.imchat.bean.ChatLogs;
import com.zps.imchat.jsonbean.MsgJson;
import com.zps.imchat.rabbitmq.MqServer;
import com.zps.imchat.service.ChatLogsService;
import com.zps.imchat.service.GroupUserService;
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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zps
 * @desc : 消息核心处理类
 **/
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    RabbitmqUtil rabbitmqUtil = SpringBeanUtil.getBean(RabbitmqUtil.class);

    private static final Logger log = LoggerFactory.getLogger(WebSocketServerHandler.class);

    /**保存连接进来的用户的chanel */
    public static ChannelGroup userMap = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

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
                 chatLogs.setStatus(1);  //1代表未读

                 /** 保存到数据库 */
                 ChatLogsService chatLogsService = SpringBeanUtil.getBean(ChatLogsService.class);
                 chatLogsService.saveLogs(chatLogs);

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
                     System.out.println(JsonUtil.pojoTojson(msgData));
                 }
                 break;

             case "groupChat" :   //群聊

                 /**
                  * 相应的离线消息保存，未完成
                  */

                 String groupid = msgData.getDataMap().get("groupid");
                 GroupUserService groupUserService = SpringBeanUtil.getBean(GroupUserService.class);
                 List<Long> groupUsersId = groupUserService.findGroupUsersId(Long.parseLong(groupid));
                 Channel toChannel;
                 for(Long id : groupUsersId){
                      toChannel = SessionMap.get(id.toString());
                      if(toChannel == null){  //离线为读,待完成

                      }
                      toChannel = userMap.find(toChannel.id());
                      if(toChannel == null){  //用户可能过期，离线未读,待完成

                      }
                      //发送消息
                      toChannel.writeAndFlush(new TextWebSocketFrame(JsonUtil.pojoTojson(msgData)));
                 }
                 break;
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
