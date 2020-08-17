package com.pay.dao;

import com.pay.entity.LcnPay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (LcnPay)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-13 10:12:32
 */

@Mapper
public interface LcnPayDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    LcnPay queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<LcnPay> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param lcnPay 实例对象
     * @return 对象列表
     */
    List<LcnPay> queryAll(LcnPay lcnPay);

    /**
     * 新增数据
     *
     * @param lcnPay 实例对象
     * @return 影响行数
     */
    int insert(LcnPay lcnPay);

    /**
     * 修改数据
     *
     * @param lcnPay 实例对象
     * @return 影响行数
     */
    int update(LcnPay lcnPay);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}