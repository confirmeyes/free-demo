package com.order.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (OrderEvent)实体类
 *
 * @author makejava
 * @since 2020-08-18 15:32:51
 */
public class OrderEvent implements Serializable {
    private static final long serialVersionUID = 965580212879141654L;
    /**
    * id
    */
    private Integer id;
    /**
    * 订单类型，是否修改订单表状态
    */
    private Integer ordertype;
    /**
    * new,published,processed,end
    */
    private String process;
    /**
    * 件内容，保存事件发生时需要传递的数据
    */
    private String content;
    /**
    * 事件创建时间
    */
    private Date createtime;
    /**
    * 事件更新时间
    */
    private Date updatetime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(Integer ordertype) {
        this.ordertype = ordertype;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}