package redisSimple.jedisSimple;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * for simple set redis test
 *
 * Author: zirui
 * Date: 2021/7/19
 */
@Slf4j
public class SetDemoTest {
    @Test
    public void operateList() {
        // connect
        Jedis jedis = new Jedis("192.168.1.2", 6379);
        log.warn("jedis.ping(): {}", jedis.ping());

        // sadd
        jedis.sadd("set1", "u1", "u2", "u3");
        log.warn("jedis.smembers(): {}", jedis.smembers("set1"));

        // scard: get the elements numbers in set
        log.warn("jedis.scard(): {}", jedis.scard("set1"));

        // sismember, whether element in the set
        log.warn("jedis.sismember(): {}", jedis.sismember("set1", "u4"));

        //  srem: del element
        log.warn("jedis.srem(): {}", jedis.srem("set1", "u3"));

        log.warn("jedis.smembers(): {}", jedis.smembers("set1"));

        jedis.close();
    }
}
