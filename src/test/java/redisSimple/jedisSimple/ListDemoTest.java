package redisSimple.jedisSimple;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * for simple list redis test
 *
 * Author: zirui
 * Date: 2021/7/19
 */
@Slf4j
public class ListDemoTest {
    @Test
    public void operateList() {
        // connect
        Jedis jedis = new Jedis("192.168.1.2", 6379);
        log.warn("jedis.ping(): {}", jedis.ping());
        jedis.del("list1");

        // add 3 element to the list tail
        jedis.rpush("list1", "1", "2", "3");
        log.warn("jedis.type(): {}", jedis.type("list1"));

        // get all elements
        log.warn("jedis.lrange(0, -1): {}", jedis.lrange("list1", 0, -1));
        // get elements from 1 to 2
        log.warn("jedis.lrange(1, 2): {}", jedis.lrange("list1", 1, 2));

        // get length
        log.warn("jedis.llen(list1): {}", jedis.llen("list1"));

        log.warn("jedis.lindex(list1, 1): {}", jedis.lindex("list1", 1));

        log.warn("jedis.lpop(list1): {}", jedis.lpop("list1"));
        log.warn("jedis.rpop(list1): {}", jedis.rpop("list1"));

        // set the element whose index is 0
        jedis.lset("list1", 0, "new list1");
        log.warn("jedis.lrange(0, -1): {}", jedis.lrange("list1", 0, -1));

        jedis.close();
    }
}
