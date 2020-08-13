package com.pay.service.impl;

import com.pay.entity.LcnPay;
import com.pay.dao.LcnPayDao;
import com.pay.service.LcnPayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (LcnPay)表服务实现类
 *
 * @author makejava
 * @since 2020-08-13 10:12:33
 */
@Service("lcnPayService")
public class LcnPayServiceImpl implements LcnPayService {
    @Resource
    private LcnPayDao lcnPayDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public LcnPay queryById(Integer id) {
        return this.lcnPayDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<LcnPay> queryAllByLimit(int offset, int limit) {
        return this.lcnPayDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param lcnPay 实例对象
     * @return 实例对象
     */
    @Override
    public LcnPay insert(LcnPay lcnPay) {
        this.lcnPayDao.insert(lcnPay);
        return lcnPay;
    }

    /**
     * 修改数据
     *
     * @param lcnPay 实例对象
     * @return 实例对象
     */
    @Override
    public LcnPay update(LcnPay lcnPay) {
        this.lcnPayDao.update(lcnPay);
        return this.queryById(lcnPay.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.lcnPayDao.deleteById(id) > 0;
    }
}