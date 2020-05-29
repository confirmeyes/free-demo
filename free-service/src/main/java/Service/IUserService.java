package Service;

import entity.User;

/**
 * @author lpx .
 * @create 2020-03-08-14:53 .
 * @description .
 */
public interface IUserService {

    User findUserById(Integer id);
}
