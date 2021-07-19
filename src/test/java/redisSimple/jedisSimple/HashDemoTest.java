package redisSimple.jedisSimple;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * for simple hashtable redis test
 *
 * Author: zirui
 * Date: 2021/7/19
 */
@Slf4j
public class HashDemoTest {
    @Test
    public void operateList() {
        // connect
        Jedis jedis = new Jedis("192.168.1.2", 6379);
        log.warn("jedis.ping(): {}", jedis.ping());

        // set hash key-value pair
        jedis.hset("config", "ip", "127.0.0.1");
        // get the value
        log.warn("jedis.hget(): {}", jedis.hget("config", "ip"));

        // get type
        log.warn("jedis.type(): {}", jedis.type("config"));

        // batch insertion
        Map<String, String> map = new HashMap<>();
        map.put("port", "8080");
        map.put("maxalive", "3600");
        map.put("weight", "1.0");
        jedis.hmset("config", map);

        // get all
        log.warn("jedis.hgetAll(): {}", jedis.hgetAll("config"));
        // get parts
        log.warn("jedis.hmget(): {}", jedis.hmget("config", "ip", "port"));

        jedis.hincrByFloat("config", "weight", 1.2);
        log.warn("jedis.hget(weight): {}", jedis.hget("config", "weight"));

        log.warn("jedis.hkeys(config): {}", jedis.hkeys("config"));
        log.warn("jedis.hvals(config): {}", jedis.hvals("config"));

        // get len
        log.warn("jedis.hlen(): {}", jedis.hlen("config"));

        // exists?
        log.warn("jedis.hexists(weight): {}", jedis.hexists("config", "weight"));

        // del
        jedis.hdel("config", "weight");
        log.warn("jedis.hexists(weight): {}", jedis.hexists("config", "weight"));

        jedis.close();
    }
}
