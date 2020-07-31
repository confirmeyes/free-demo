import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.UserController;
import spring.UserService;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @author WIN10 .
 * @create 2020-05-29-10:14 .
 * @description .
 */
public class SpringDemoTest {

    private static final Logger log = LoggerFactory.getLogger(SpringDemoTest.class);

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

    public void testSpi() throws Exception {
        ClassLoader classLoader = SpringDemoTest.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("D:\\ideaProjects\\free-demo\\free-web\\src\\main\\resources\\config\\application.yml");

        Properties properties = new Properties();
        properties.load(inputStream);
        System.out.println(properties);
    }


    public void testShutdownHook() throws Exception {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("--- ShutdownHook ---");
        }));
        Thread.sleep(10000000);
    }

    public void testBeanFactory() throws Exception {
        //ApplicationContext applicationContext = ClassPathXmlApplicationContext(applicationContext.xml);
        //applicationContext.getBean("&BeanFactory");
    }
}
