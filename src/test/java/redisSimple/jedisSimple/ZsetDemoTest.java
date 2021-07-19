package redisSimple.jedisSimple;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * for simple Zset redis test
 *
 * Author: zirui
 * Date: 2021/7/19
 */
@Slf4j
public class ZsetDemoTest {
    @Test
    public void operateList() {
        // connect
        Jedis jedis = new Jedis("192.168.1.2", 6379);
        log.warn("jedis.ping(): {}", jedis.ping());

        Map<String, Double> map = new HashMap<>();
        map.put("u1", 1000.0);
        map.put("u2", 2000.0);
        map.put("u3", 3000.0);
        map.put("u4", 12000.0);
        map.put("u5", 13000.0);

        // add zset
        jedis.zadd("salary", map);

        // get elements num
        log.warn("jedis.zcard(): {}", jedis.zcard("salary"));
        // get elements from [x, y]
        log.warn("jedis.zrange(): {}", jedis.zrange("salary", 0, -1));
        // get elements from [x, y] in reverse order
        log.warn("jedis.zrevrange(): {}", jedis.zrevrange("salary", 0, -1));

        // get elements from salary [x, y]
        log.warn("jedis.zrangeByScore(): {}", jedis.zrangeByScore("salary", 1000, 10000));

        // get elements from salary [x, y], and return them
        Set<Tuple> res0 = jedis.zrangeByScoreWithScores("salary", 1000, 10000);
        for (Tuple t : res0) {
            log.warn("Tuple.get(): {} -> {}", t.getElement(), t.getScore());
        }

        // get elements from salary [x, y] in rev order
        log.warn("jedis.zrevrangeByScore(): {}", jedis.zrevrangeByScore("salary", 1000, 10000));

        // get elements num from salary [x, y]
        log.warn("jedis.zcount(): {}", jedis.zcount("salary", 1000, 10000));

        // get element score
        log.warn("jedis.zscore(): {}", jedis.zscore("salary", "u1"));

        // get element rank
        log.warn("jedis.zrank(): {}", jedis.zrank("salary", "u2"));

        // get element rank, after rev
        log.warn("jedis.zrevrank(): {}", jedis.zrevrank("salary", "u2"));

        // del
        log.warn("jedis.zrem(): {}", jedis.zrem("salary", "u1", "u2"));

        // del by rank
        log.warn("jedis.zremrangeByRank(): {}", jedis.zremrangeByRank("salary", 0, 1));

        // del by scores scale
        log.warn("jedis.zremrangeByScore(): {}", jedis.zremrangeByScore("salary", 12500, 20000));

        log.warn("jedis.zrange(): {}", jedis.zrange("salary", 0, -1));


        map = new HashMap<>();
        map.put("u1", 1000.0);
        map.put("u2", 2000.0);
        map.put("u3", 3000.0);
        map.put("u4", 12000.0);
        map.put("u5", 13000.0);
        jedis.zadd("salary", map);

        // incr specific element
        log.warn("jedis.zincrby(): {}", jedis.zincrby("salary", 1000, "u1"));

        res0 = jedis.zrangeByScoreWithScores("salary", 1000, 100000);
        for (Tuple t : res0) {
            log.warn("Tuple.get(): {} -> {}", t.getElement(), t.getScore());
        }

        jedis.close();
    }
}
