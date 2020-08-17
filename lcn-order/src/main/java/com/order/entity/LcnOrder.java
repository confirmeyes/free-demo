package com.order.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (LcnOrder)实体类
 *
 * @author makejava
 * @since 2020-08-13 10:14:13
 */

@Data
public class LcnOrder implements Serializable {
    private static final long serialVersionUID = 697056002783030678L;
    /**
    * id
    */
    private Integer id;
    /**
    * name
    */
    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}