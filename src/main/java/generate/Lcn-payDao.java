package generate;

import generate.Lcn-pay;

public interface Lcn-payDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Lcn-pay record);

    int insertSelective(Lcn-pay record);

    Lcn-pay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Lcn-pay record);

    int updateByPrimaryKey(Lcn-pay record);
}