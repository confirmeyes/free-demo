package ServiceImpl;

import Service.IUserService;
import entity.User;

/**
 * @author lpx .
 * @create 2020-03-08-14:54 .
 * @description .
 */
public class userServiceImpl implements IUserService {
    @Override
    public User findUserById(Integer id) {
        return new User(id, "Alice");
    }
}
