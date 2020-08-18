package com.pay.service.impl;

import com.pay.entity.OrderEvent;
import com.pay.dao.OrderEventDao;
import com.pay.service.OrderEventService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (OrderEvent)表服务实现类
 *
 * @author makejava
 * @since 2020-08-18 15:33:21
 */
@Service("orderEventService")
public class OrderEventServiceImpl implements OrderEventService {
    @Resource
    private OrderEventDao orderEventDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OrderEvent queryById(Integer id) {
        return this.orderEventDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<OrderEvent> queryAllByLimit(int offset, int limit) {
        return this.orderEventDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param orderEvent 实例对象
     * @return 实例对象
     */
    @Override
    public OrderEvent insert(OrderEvent orderEvent) {
        this.orderEventDao.insert(orderEvent);
        return orderEvent;
    }

    /**
     * 修改数据
     *
     * @param orderEvent 实例对象
     * @return 实例对象
     */
    @Override
    public OrderEvent update(OrderEvent orderEvent) {
        this.orderEventDao.update(orderEvent);
        return this.queryById(orderEvent.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.orderEventDao.deleteById(id) > 0;
    }
}