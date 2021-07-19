package redisSimple.springRedis;

import lombok.extern.slf4j.Slf4j;
import redisSimple.model.User;

/**
 * UserServiceImplWithTemplate
 *
 * Author: zirui
 * Date: 2021/7/19
 */
@Slf4j
public class UserServiceImplWithTemplate implements UserService {
    private static final String USER_UID_PREFIX = "user:uid:";

    private static final long CACHE_LONG = 60 * 4;

    protected CacheOperationService cacheOperationService;

    public void setCacheOperationService(CacheOperationService cacheOperationService) {
        this.cacheOperationService = cacheOperationService;
    }

    @Override
    public User getUser(long id) {
        String key = USER_UID_PREFIX + id;
        User value = (User) cacheOperationService.get(key);

        return value;
    }

    @Override
    public User saveUser(final User user) {
        String key = USER_UID_PREFIX + user.getUid();
        log.warn("user: {}", user);

        cacheOperationService.set(key, user, CACHE_LONG);

        return user;
    }

    @Override
    public void deleteUser(long id) {
        String key = USER_UID_PREFIX + id;
        cacheOperationService.del(key);

        log.warn("delete user: {}", id);
    }

    @Override
    public void deleteAll() {

    }
}
