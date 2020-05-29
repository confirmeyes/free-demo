import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spring.UserController;
import spring.UserService;

import java.lang.reflect.Field;

/**
 * @author WIN10 .
 * @create 2020-05-29-10:14 .
 * @description .
 */
public class SpringDemoTest {

    private static final Logger log = LoggerFactory.getLogger(SpringDemoTest.class);

    @Test
    public void test() throws Exception {

        UserController userController = new UserController();
        Class<? extends UserController> claz = userController.getClass();
        log.info(claz.getName());

        Field userServiceField = claz.getDeclaredField("userService");
        UserService userService = new UserService();
        userServiceField.setAccessible(true);
        userServiceField.set(userController, userService);
        System.out.println(userController.getUserService());
    }
}
