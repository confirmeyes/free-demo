package com.order.controller;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.order.entity.LcnOrder;
import com.order.entity.LcnPay;
import com.order.service.LcnOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;

/**
 * (LcnOrder)表控制层
 *
 * @author makejava
 * @since 2020-08-13 10:14:16
 */
@RestController
@RequestMapping("/lcnOrder")
public class LcnOrderController {
    /**
     * 服务对象
     */
    @Resource
    private LcnOrderService lcnOrderService;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    /**
     * 开启负载均衡
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    public LcnOrder selectOne(Integer id) {
        return this.lcnOrderService.queryById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    @GetMapping("/insertOne")
    public String insertOne(LcnOrder lcnOrder) {
        ServiceInstance choose = loadBalancerClient.choose("lcn-pay");
        LcnOrder order = lcnOrderService.insert(lcnOrder);
        String url = "http://" + "localhost" + ":" + choose.getPort() + "/lcnPay/selectPay?id={id}";
        System.out.println(url);

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Integer> map = Collections.singletonMap("id", order.getId());
        return restTemplate.getForObject(url, String.class, map);

    }

}