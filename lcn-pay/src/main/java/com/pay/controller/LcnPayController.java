package com.pay.controller;

import com.pay.entity.LcnPay;
import com.pay.service.LcnPayService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (LcnPay)表控制层
 *
 * @author makejava
 * @since 2020-08-13 10:12:33
 */
@RestController
@RequestMapping("lcnPay")
public class LcnPayController {
    /**
     * 服务对象
     */
    @Resource
    private LcnPayService lcnPayService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public LcnPay selectOne(Integer id) {
        return this.lcnPayService.queryById(id);
    }

}