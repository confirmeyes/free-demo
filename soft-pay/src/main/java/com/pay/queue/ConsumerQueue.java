package com.pay.queue;

import com.pay.dao.OrderEventDao;
import com.pay.entity.OrderEvent;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author WIN10 .
 * @create 2020-08-24-16:29 .
 * @description .
 */

@Component
public class ConsumerQueue {

    @Autowired
    private OrderEventDao orderEventDao;

    @JmsListener(destination = "ActiveMQQueue", containerFactory = "jmsListenerContainerFactory")
    public void receive(TextMessage textMessage, Session session) throws JMSException {
        try {
            System.out.println("收到的消息：" + textMessage.getText());
            String content = textMessage.getText();

            OrderEvent orderEvent = (OrderEvent) JSONObject.toBean(JSONObject.fromObject(content), OrderEvent.class);
            orderEventDao.insert(orderEvent);
            // 业务完成，确认消息 消费成功
            textMessage.acknowledge();
        } catch (Exception e) {
            // 回滚消息
            e.printStackTrace();
//            e.getMessage(); // 放到log中。
            System.out.println("异常了");
            session.recover();
        }

    }

    /**
     * 补偿 处理（人工，脚本）。自己根据自己情况。
     *
     * @param text
     */
    @JmsListener(destination = "DLQ.ActiveMQQueue")
    public void receive2(String text) {
        System.out.println("死信队列:" + text);
    }
}
