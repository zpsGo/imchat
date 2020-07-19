package com.zps.imchat.rabbitmq;

import com.zps.imchat.service.UserService;
import com.zps.imchat.utils.RabbitmqUtil;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: zps
 * @desc : 启动Mq服务，并为每一个用户创建一个queue,用于保存离线消息
 **/
@Component
public class MqServer {

    @Autowired
    UserService userService;

    @Autowired
    RabbitmqUtil rabbitmqUtil;

    public final static String EXCHANGE = "imchat_exchange";
    public final static String QUEUE = "imchat_";

    //创建交换机
    public final static TopicExchange IM_exchange = new TopicExchange(EXCHANGE);

    //初始化所以用户的队列，并与交换机进行绑定
    public void init(){
        //获取所有用户的id
        List<Long> list = userService.getUserId();
        rabbitmqUtil.addExchange(IM_exchange);

        for(Long id : list){
            Queue queue = new Queue(QUEUE + id);
            rabbitmqUtil.addQueue(queue);
            rabbitmqUtil.addBinding(queue , IM_exchange , QUEUE + id);
        }
    }


}
