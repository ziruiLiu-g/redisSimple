package redisSimple.springRedis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redisSimple.model.User;

import static org.junit.Assert.*;

/**
 * UserServiceImplWithAnnoTest tester
 *
 * Author: zirui
 * Date: 2021/7/19
 */
@Slf4j
public class UserServiceImplWithAnnoTest {
    @Test
    public void testServiceImplWithAnno() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-redis.xml");

        UserService userService = (UserService) ac.getBean("serviceImplWithAnno");
        long userId = 1L;
        userService.deleteUser(userId);
        log.warn("del user: {}", userService.getUser(userId));

        User user = new User();
        user.setUid("1");
        user.setNickName("one");
        userService.saveUser(user);
        log.warn("save / read user: {}", userService.getUser(userId));
    }
}