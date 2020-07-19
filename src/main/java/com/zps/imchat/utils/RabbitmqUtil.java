package com.zps.imchat.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zps
 * @desc : Rabbitmq工具类：动态创建queue,动态绑定，获取消息等等
 **/
@Configuration
public class RabbitmqUtil {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqUtil.class);

    private final RabbitAdmin rabbitAdmin;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitmqUtil(RabbitAdmin rabbitAdmin, RabbitTemplate rabbitTemplate){
        this.rabbitAdmin = rabbitAdmin;
        this.rabbitTemplate = rabbitTemplate;
    }


    /**
     * 有绑定Key的Exchange发送
     * @param routingKey ： 路由键
     * @param msg : 需要发送的消息对象
     */
    public void sendMessageToExchange(TopicExchange topicExchange, String routingKey, Object msg){

        rabbitTemplate.convertAndSend(topicExchange.getName(), routingKey, msg);
    }


    /**
     * 创建Exchange
     * @param exchange ： 创建的交换机类型
     */
    public void addExchange(AbstractExchange exchange){
        rabbitAdmin.declareExchange(exchange);
    }

    /**
     * 删除一个Exchange
     * @param exchangeName：删除交换机的名字
     */
    public boolean deleteExchange(String exchangeName){

        return rabbitAdmin.deleteExchange(exchangeName);

    }


    /**
     * durable = false.
     * @return Queue
     */
    public Queue addQueue(){
        return rabbitAdmin.declareQueue();
    }

    /**
     * 创建一个指定的Queue
     * @param queue
     * @return queueName
     */
    public String addQueue(Queue queue){
        return rabbitAdmin.declareQueue(queue);
    }

    /**
     * 删除指定的queue
     */
    public void deleteQueue(String queueName, boolean unused, boolean empty){
        rabbitAdmin.deleteQueue(queueName,unused,empty);
    }

    /**
     * 删除一个queue
     * @param queueName
     */
    public boolean deleteQueue(String queueName){
        return rabbitAdmin.deleteQueue(queueName);
    }

    /**
     * 绑定一个队列到一个匹配型交换器使用一个routingKey
     * @param queue : 队列名称
     * @param exchange ： 交换机名称
     * @param routingKey：路由键
     */
    public void addBinding(Queue queue ,TopicExchange exchange,String routingKey){
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
        rabbitAdmin.declareBinding(binding);
    }

    /**
     * 绑定一个Exchange到一个匹配型Exchange 使用一个routingKey
     * @param exchange
     * @param topicExchange
     * @param routingKey
     */
    public void addBinding(Exchange exchange, TopicExchange topicExchange, String routingKey){
        Binding binding = BindingBuilder.bind(exchange).to(topicExchange).with(routingKey);
        rabbitAdmin.declareBinding(binding);
    }

    /**
     * 去掉一个binding
     * @param binding
     */
    public void removeBinding(Binding binding){
        rabbitAdmin.removeBinding(binding);
    }

    /**
     * 从指定队列中获取离线消息
     * @param queue ： 队列名称
     * @return
     */
    public Object getMsgFromQueue(String queue) {
        return rabbitTemplate.receiveAndConvert(queue);
    }
}
