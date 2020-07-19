package com.zps.imchat;

import com.zps.imchat.bean.MyFz;
import com.zps.imchat.utils.RabbitmqUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImchatApplication.class)
class ImchatApplicationTests {

    @Autowired
    RabbitmqUtil rabbitmqUtil;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
//        rabbitmqUtil.addQueue(new Queue("imchat_1"));
//        System.out.println("创建队列成功！");
        System.out.println("开始创建队列");
        TopicExchange exchange = new TopicExchange("imchat_exchange");
        rabbitmqUtil.addExchange(exchange);
        for(int i = 0 ; i < 5 ; i ++){
            Queue queue = new Queue("imchat_" + i);
            rabbitmqUtil.addQueue(queue);
            rabbitmqUtil.addBinding(queue , exchange , "imchat_" + i);
        }
        for(int i = 0 ; i < 5; i++) {
            MyFz myFz = new MyFz();
            myFz.setFzId((long) 1);
            myFz.setFzGroupname("测试组");
            rabbitmqUtil.sendMessageToExchange(exchange , "imchat_1" , myFz);
            Object msg = rabbitTemplate.receiveAndConvert("imchat_1");
            System.out.println(msg.getClass());
            myFz = (MyFz) msg;
            System.out.println(((MyFz) msg).getFzGroupname());
//            Map<String, Object> map = new HashMap<>();
//            map.put("msg", "这是第" + i + "个消息");
//            map.put("data", Arrays.asList("helloword", 123, true));
//            rabbitmqUtil.sendMessageToExchange(exchange, "imchat_1", map);
        }

    }
    @Test
    void print(){
        Object msg = rabbitTemplate.receiveAndConvert("imchat_1");
        while (msg != null) {
            System.out.println(msg.getClass());
            System.out.println(msg);
            msg = rabbitTemplate.receiveAndConvert("imchat_1");
        }
    }


}
