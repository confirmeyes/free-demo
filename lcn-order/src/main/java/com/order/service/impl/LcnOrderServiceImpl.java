package com.order.service.impl;

import com.order.entity.LcnOrder;
import com.order.dao.LcnOrderDao;
import com.order.service.LcnOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (LcnOrder)表服务实现类
 *
 * @author makejava
 * @since 2020-08-13 10:14:15
 */
@Service("lcnOrderService")
public class LcnOrderServiceImpl implements LcnOrderService {
    @Resource
    private LcnOrderDao lcnOrderDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public LcnOrder queryById(Integer id) {
        return this.lcnOrderDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<LcnOrder> queryAllByLimit(int offset, int limit) {
        return this.lcnOrderDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param lcnOrder 实例对象
     * @return 实例对象
     */
    @Override
    public LcnOrder insert(LcnOrder lcnOrder) {
        this.lcnOrderDao.insert(lcnOrder);
        return lcnOrder;
    }

    /**
     * 修改数据
     *
     * @param lcnOrder 实例对象
     * @return 实例对象
     */
    @Override
    public LcnOrder update(LcnOrder lcnOrder) {
        this.lcnOrderDao.update(lcnOrder);
        return this.queryById(lcnOrder.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.lcnOrderDao.deleteById(id) > 0;
    }
}