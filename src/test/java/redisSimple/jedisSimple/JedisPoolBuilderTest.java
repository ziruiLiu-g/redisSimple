package redisSimple.jedisSimple;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

/**
 * use JedisPool
 *
 * Author: zirui
 * Date: 2021/7/19
 */
@Slf4j
public class JedisPoolBuilderTest {
    private static final int NUM = 200;

    private static final String ZSET_KEY = "zset1";

    @Test
    public void testDel() {
        Jedis redis = null;

        try {
            redis = JedisPoolBuilder.getJedis();

            redis.del(ZSET_KEY);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    @Test
    public void testSet() {
        testDel();

        try (Jedis jedis = JedisPoolBuilder.getJedis()) {
            int loop = 0;

            while (loop < NUM) {
                jedis.zadd(ZSET_KEY, loop, "field-" + loop);
                loop++;
            }
        }
    }
}