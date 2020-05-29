package entity;

/**
 * @author lpx .
 * @create 2020-03-08-15:02 .
 * @description .
 */

import java.io.Serializable;


public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer age;
    private String name;
    private String remark;

    public User(Integer id, String name) {
        this.age = id;
        this.name = name;
    }

    public User() {
    }

    public Integer getId() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.age = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + age +
                ", name='" + name + ", remark='" + remark + '\'' +
                '}';
    }
}