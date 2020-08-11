package entity;

/**
 * @author lpx .
 * @create 2020-03-08-15:02 .
 * @description .
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 6851455035229006654L;
    private Integer id;
    private Integer age;
    private String name;
    private String remark;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}