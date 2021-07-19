package redisSimple.jedisSimple;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * for simple string redis test
 *
 * Author: zirui
 * Date: 2021/7/19
 */
@Slf4j
public class StringDemoTest {
    @Test
    public void OperateString() {
        // connect
        Jedis jedis = new Jedis("192.168.1.2", 6379);
        log.warn("jedis.ping(): {}", jedis.ping());

        // set string
        jedis.set("key0", "123456");
        log.warn("jedis.type(key0): {}", jedis.type("key0"));
        log.warn("jedis.get(key0): {}", jedis.get("key0"));
        log.warn("jedis.exists(key0): {}", jedis.exists("key0"));
        log.warn("jedis.strlen(key0): {}", jedis.strlen("key0"));
        log.warn("jedis.getrange(key0): {}", jedis.getrange("key0", 0, -1));
        log.warn("jedis.getrange(key0): {}", jedis.getrange("key0", 1, 4));
        log.warn("jedis.append(key0): {}", jedis.append("key0", "appendStr"));
        log.warn("jedis.get(key0): {}", jedis.get("key0"));

        // rename
        jedis.rename("key0", "key0_v2");
        log.warn("jedis.exists(key0_v2): {}", jedis.exists("key0_v2"));

        // batch insertion
        jedis.mset("key1", "val1", "key2", "val2", "key3", "100");
        log.warn("jedis.mget(key1,key2,key3): {}", jedis.mget("key1", "key2", "key3"));

        // delete
        log.warn("jedis.del(key1): {}", jedis.del("key1"));
        log.warn("jedis.exists(key1): {}", jedis.exists("key1"));
        log.warn("jedis.getSet(key2): {}", jedis.getSet("key2", "val3"));

        // incre
        log.warn("jedis.incr(key3): {}", jedis.incr("key3"));
        log.warn("jedis.incrBy(key3): {}", jedis.incrBy("key3", 15));
        // decr
        log.warn("jedis.decr(key3): {}", jedis.decr("key3"));
        log.warn("jedis.incrBy(key3): {}", jedis.decrBy("key3", 15));

        // add float
        log.warn("jedis.incrByFloat(key3): {}", jedis.incrByFloat("key3", 1.5));

        log.warn("jedis.setnx(key3): {}", jedis.setnx("key3", "exists2"));
        log.warn("jedis.get(key3): {}", jedis.get("key3"));

        log.warn("jedis.msetnx(key2, key3): {}", jedis.msetnx("key2", "exists1", "key3", "exists2"));
        log.warn("jedis.mget(key2, key3): {}", jedis.mget("key2", "key3"));

        // set key, expired after 2s
        jedis.setex("key4", 2, "2 seconds expire");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.warn("jedis.get(key4): {}", jedis.get("key4"));

        jedis.set("key6", "123456789");
        // start from index 3, use the new value to cover the old one
        jedis.setrange("key6", 3, "adcdefg");
        log.warn("jedis.get(key6): {}", jedis.get("key6"));

        // return all key that with "key" prefix
        log.warn("jedis.get(key*): {}", jedis.keys("key*"));

        jedis.close();
    }
}