package spring;

/**
 * @author WIN10 .
 * @create 2020-05-29-10:14 .
 * @description .
 */

public class UserController {

    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
