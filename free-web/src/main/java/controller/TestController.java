package controller;

import Service.IUserService;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WIN10 .
 * @create 2020-05-27-10:41 .
 * @description .
 */

@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);


    @GetMapping("/hello")
    public User hello(int id) {

        User user = new User(1, "1", 12, "123");
        if (id > 1) {
            user.setId(3);
        }
        if (user.getId().equals(3)) {
            logger.debug("Processing trade with id: {} and name: {}", id, user.getName());
            user.setId(3);
        }
        return user;
    }
}
