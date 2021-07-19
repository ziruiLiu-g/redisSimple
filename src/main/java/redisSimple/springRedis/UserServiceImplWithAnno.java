package redisSimple.springRedis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import redisSimple.model.User;

/**
 * UserServiceImplWithAnno
 *
 * Author: zirui
 * Date: 2021/7/19
 */
@Slf4j
@CacheConfig(cacheNames = "userCache")
public class UserServiceImplWithAnno implements UserService {
    private static final String USER_UID_PREFIX = "'user:uid:'+";

    @Cacheable(key = USER_UID_PREFIX + "T(String).valueOf(#id)")
    @Override
    public User getUser(long id) {
        log.warn("user: is null");
        return null;
    }

    @CachePut(key = USER_UID_PREFIX + "T(String).valueOf(#user.uid)")
    @Override
    public User saveUser(User user) {
        log.warn("user: save to redis");
        return user;
    }

    @CacheEvict(key = USER_UID_PREFIX + "T(String).valueOf(#id)")
    @Override
    public void deleteUser(long id) {
        log.warn("delete user: id");
    }

    @Override
    public void deleteAll() {

    }
}
