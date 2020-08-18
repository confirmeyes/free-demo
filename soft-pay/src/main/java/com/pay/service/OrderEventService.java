package com.pay.service;

import com.pay.entity.OrderEvent;
import java.util.List;

/**
 * (OrderEvent)表服务接口
 *
 * @author makejava
 * @since 2020-08-18 15:33:21
 */
public interface OrderEventService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderEvent queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<OrderEvent> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param orderEvent 实例对象
     * @return 实例对象
     */
    OrderEvent insert(OrderEvent orderEvent);

    /**
     * 修改数据
     *
     * @param orderEvent 实例对象
     * @return 实例对象
     */
    OrderEvent update(OrderEvent orderEvent);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}