package com.pay.controller;

import com.pay.entity.OrderEvent;
import com.pay.service.OrderEventService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (OrderEvent)表控制层
 *
 * @author makejava
 * @since 2020-08-18 15:33:21
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