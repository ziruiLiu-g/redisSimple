package redisSimple.jedisSimple;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * JedisPoolBuilder
 *
 * Author: zirui
 * Date: 2021/7/19
 */
@Slf4j
public class JedisPoolBuilder {
    public static final int MAX_IDEL = 50;

    public static final int MAX_TOTAL = 50;

    public static JedisPool pool = null;

    static {
        buildPool();

        hotPool();
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    private static void buildPool() {
        if (null == pool) {
            long start = System.currentTimeMillis();
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_TOTAL);
            config.setMaxIdle(MAX_IDEL);
            config.setMaxWaitMillis(1000 * 10);

            // check whether the jedis instance available before borrow
            config.setTestOnBorrow(true);

            // new jedis(CONFIG, ADDR, PORT, TIMEOUT)
            pool = new JedisPool(config, "192.168.1.2", 6379, 10000);
            long end = System.currentTimeMillis();

            log.warn("build pool cost: {}(ms)", end - start);
        }

    }

    /**
     * hot pool
     * redis will not add idel in the pool when the first time it start
     *
     * so we should pre hot the pool(add jedis instance into it).
     * the operation can improve the efficiency
     */
    public static void hotPool() {
        long start = System.currentTimeMillis();
        List<Jedis> minIdelJedisList = new ArrayList<>(MAX_IDEL);
        Jedis jedis = null;

        for (int i = 0; i < MAX_IDEL; i++) {
            try {
                jedis = pool.getResource();
                minIdelJedisList.add(jedis);
                jedis.ping();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        for (int i = 0; i < MAX_IDEL; i++) {
            try {
                jedis = minIdelJedisList.get(i);
                jedis.close();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        long end = System.currentTimeMillis();
        log.warn("hot pool cost: {}(ms)", end - start);
    }
}
