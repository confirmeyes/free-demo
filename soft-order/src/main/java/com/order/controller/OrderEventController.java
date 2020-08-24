package com.order.controller;

import com.order.entity.OrderEvent;
import com.order.service.OrderEventService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (OrderEvent)表控制层
 *
 * @author makejava
 * @since 2020-08-18 15:32:53
 */
@RestController
@RequestMapping("orderEvent")
public class OrderEventController {
    /**
     * 服务对象
     */
    @Resource
    private OrderEventService orderEventService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public OrderEvent selectOne(Integer id) {
        return this.orderEventService.queryById(id);
    }




}