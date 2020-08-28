package com.order.task;

import com.order.dao.OrderEventDao;
import com.order.entity.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Queue;
import java.util.List;

import net.sf.json.JSONObject;

/**
 * @author WIN10 .
 * @create 2020-08-24-14:51 .
 * @description .
 */

@Component
public class ProduceTask {

    @Autowired
    private Queue queue;

    @Autowired
    private OrderEventDao orderEventDao;

    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;

    @Scheduled(cron = "0/5 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void task() {
        System.out.println("定时任务");

        List<OrderEvent> tblOrderEventList = orderEventDao.selectByOrderType(1);
        for (int i = 0; i < tblOrderEventList.size(); i++) {
            OrderEvent event = tblOrderEventList.get(i);

            // 更改这条数据的orderType为2
            orderEventDao.updateEvent(event.getOrdertype());

            System.out.println("修改数据库完成 ordertype ==> 2");
            jmsMessagingTemplate.convertAndSend(queue, JSONObject.fromObject(event).toString());
            int a = 1/0;
        }
    }
}