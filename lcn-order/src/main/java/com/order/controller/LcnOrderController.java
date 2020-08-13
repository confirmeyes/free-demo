package com.order.controller;

import com.order.entity.LcnOrder;
import com.order.service.LcnOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (LcnOrder)表控制层
 *
 * @author makejava
 * @since 2020-08-13 10:14:16
 */
@RestController
@RequestMapping("lcnOrder")
public class LcnOrderController {
    /**
     * 服务对象
     */
    @Resource
    private LcnOrderService lcnOrderService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public LcnOrder selectOne(Integer id) {
        return this.lcnOrderService.queryById(id);
    }

}