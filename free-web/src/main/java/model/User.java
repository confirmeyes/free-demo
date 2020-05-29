package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WIN10 .
 * @create 2020-05-15-15:45 .
 * @description .
 */

@Getter
@Setter
@AllArgsConstructor
public class User {

    private Integer id;
    private String name;
    private Integer age;
    private String gender;


}
