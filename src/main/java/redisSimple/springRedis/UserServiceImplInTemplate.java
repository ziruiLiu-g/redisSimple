package redisSimple.springRedis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import redisSimple.model.User;

import java.nio.charset.StandardCharsets;

/**
 * UserServiceImplInTemplate
 *
 * Author: zirui
 * Date: 2021/7/19
 */
@Slf4j
public class UserServiceImplInTemplate implements UserService {
    private static final String USER_UID_PREFIX = "user:uid:";

    private static final long CACHE_LONG = 60 * 4;

    protected RedisTemplate redisTemplate;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public User getUser(long id) {
        User value = (User) redisTemplate.execute((RedisCallback<User>) redisConnection -> {
            byte[] key = serializeKey(USER_UID_PREFIX + id);
            if (redisConnection.exists(key)) {
                byte[] byteValue = redisConnection.get(key);
                return deSerializeValue(byteValue);
            }
            return null;
        });

        return value;
    }

    @Override
    public User saveUser(final User user) {
        redisTemplate.execute((RedisCallback<User>) redisConnection -> {
            byte[] key = serializeKey(USER_UID_PREFIX + user.getUid());
            redisConnection.set(key, user.toString().getBytes(StandardCharsets.UTF_8));
            redisConnection.expire(key, CACHE_LONG);
            return user;
        });

        return user;
    }

    @Override
    public void deleteUser(long id) {
        redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            byte[] key = serializeKey(USER_UID_PREFIX + id);
            if (connection.exists(key)) {
                connection.del(key);
            }
            return true;
        });
    }

    @Override
    public void deleteAll() {

    }

    private byte[] serializeValue(User s) {
        return redisTemplate
                .getValueSerializer().serialize(s);
    }

    private byte[] serializeKey(String s) {
        return redisTemplate
                .getKeySerializer().serialize(s);
    }

    private User deSerializeValue(byte[] b) {
        return (User) redisTemplate
                .getValueSerializer().deserialize(b);
    }
}
