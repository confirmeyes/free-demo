package com.order.dao;

import com.order.entity.OrderEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (OrderEvent)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-18 15:32:52
 */

@Mapper
public interface OrderEventDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderEvent queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<OrderEvent> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param orderEvent 实例对象
     * @return 对象列表
     */
    List<OrderEvent> queryAll(OrderEvent orderEvent);

    /**
     * 新增数据
     *
     * @param orderEvent 实例对象
     * @return 影响行数
     */
    int insert(OrderEvent orderEvent);

    /**
     * 修改数据
     *
     * @param orderEvent 实例对象
     * @return 影响行数
     */
    int update(OrderEvent orderEvent);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}