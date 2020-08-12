package dao;

import entity.Lcnpay;

public interface LcnpayDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Lcnpay record);

    int insertSelective(Lcnpay record);

    Lcnpay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Lcnpay record);

    int updateByPrimaryKey(Lcnpay record);
}