package redisSimple.redisLock;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * RedissionUtils test
 *
 * Author: zirui
 * Date: 2021/7/19
 */
@Slf4j
public class RedissionUtilsTest {
    @Test
    public void test() throws InterruptedException {
        //redisson config
        Config config = new Config();
        SingleServerConfig singleSerververConfig = config.useSingleServer();
        singleSerververConfig.setAddress("http://192.168.1.2:6379");

        //redisson client
        RedissonClient redissonClient = RedissionUtils.getInstance().getRedisson(config);
        RBucket<Object> rBucket = RedissionUtils.getInstance().getRBucket(redissonClient, "key2");

        rBucket.set("zirui");
        System.out.println(rBucket.get());

        for (int i = 0; i < 5; i++) {
            RLock lock = redissonClient.getLock("redisson:lock:" + 123);

            // arg1: wait time，arg2: timeout，arg3: time type
            // here we wait for 10s
            // if the wait time is too small, the thread will exit easily
            lock.tryLock(10, 10, TimeUnit.SECONDS);
            try {
                System.out.println(Thread.currentThread().getName() + "-> exec: get lock");
//                Thread.sleep(1000);
            } finally {
                lock.unlock();
            }
        }
    }

    @Test
    public void testMulti() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                try {
                    test();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(100000,TimeUnit.SECONDS);
    }

}