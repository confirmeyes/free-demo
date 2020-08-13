package com.pay.service;

import com.pay.entity.LcnPay;
import java.util.List;

/**
 * (LcnPay)表服务接口
 *
 * @author makejava
 * @since 2020-08-13 10:12:32
 */
public interface LcnPayService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    LcnPay queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<LcnPay> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param lcnPay 实例对象
     * @return 实例对象
     */
    LcnPay insert(LcnPay lcnPay);

    /**
     * 修改数据
     *
     * @param lcnPay 实例对象
     * @return 实例对象
     */
    LcnPay update(LcnPay lcnPay);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}