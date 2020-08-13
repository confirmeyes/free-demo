package com.pay.entity;

import java.io.Serializable;

/**
 * (LcnPay)实体类
 *
 * @author makejava
 * @since 2020-08-13 10:12:30
 */
public class LcnPay implements Serializable {
    private static final long serialVersionUID = -52452940847100125L;
    
    private Integer id;
    /**
    * 支付状态
    */
    private Long status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

}