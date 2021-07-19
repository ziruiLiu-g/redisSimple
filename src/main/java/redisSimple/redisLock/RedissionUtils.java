package redisSimple.redisLock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

/**
 * RedissionUtils
 *
 * Author: zirui
 * Date: 2021/7/19
 */
@Slf4j
public class RedissionUtils {
    private static RedissionUtils redissionUtils;

    private RedissionUtils(){}

    public static RedissionUtils getInstance(){
        if(redissionUtils==null)
            synchronized (RedissionUtils.class) {
                if(redissionUtils==null) redissionUtils=new RedissionUtils();
            }
        return redissionUtils;
    }

    /**
     * create client by redission config
     *
     * @param config config
     * @return RedissonClient
     */
    public RedissonClient getRedisson(Config config){
        RedissonClient redisson= Redisson.create(config);
        log.info("connect Redis Server");
        return redisson;
    }

    /**
     * create Redisson by ip / port
     *
     * @param ip ip
     * @param port port
     * @return RedissonClient
     */
    public RedissonClient getRedisson(String ip,String port){
        Config config=new Config();
        config.useSingleServer().setAddress(ip+":"+port);
        RedissonClient redisson=Redisson.create(config);
        log.info("connect Redis Server, ip: {}  port: {}", ip ,port);
        return redisson;
    }

    public void closeRedisson(RedissonClient redisson){
        redisson.shutdown();
        log.info("close");
    }



    /**
     * get string
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public <T> RBucket<T> getRBucket(RedissonClient redisson, String objectName){
        RBucket<T> bucket=redisson.getBucket(objectName);
        return bucket;
    }

    /**
     * get map
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public <K,V> RMap<K, V> getRMap(RedissonClient redisson,String objectName){
        RMap<K, V> map=redisson.getMap(objectName);
        return map;
    }

    /**
     * get sorted set
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public <V> RSortedSet<V> getRSortedSet(RedissonClient redisson,String objectName){
        RSortedSet<V> sortedSet=redisson.getSortedSet(objectName);
        return sortedSet;
    }

    /**
     * get set
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public <V> RSet<V> getRSet(RedissonClient redisson,String objectName){
        RSet<V> rSet=redisson.getSet(objectName);
        return rSet;
    }

    /**
     * get list
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public <V> RList<V> getRList(RedissonClient redisson, String objectName){
        RList<V> rList=redisson.getList(objectName);
        return rList;
    }

    /**
     * get queue
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public <V> RQueue<V> getRQueue(RedissonClient redisson, String objectName){
        RQueue<V> rQueue=redisson.getQueue(objectName);
        return rQueue;
    }

    /**
     * get deque
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public <V> RDeque<V> getRDeque(RedissonClient redisson, String objectName){
        RDeque<V> rDeque=redisson.getDeque(objectName);
        return rDeque;
    }

    /**
     * getlock
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public RLock getRLock(RedissonClient redisson,String objectName){
        RLock rLock=redisson.getLock(objectName);
        return rLock;
    }

    /**
     * getatomic
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public RAtomicLong getRAtomicLong(RedissonClient redisson, String objectName){
        RAtomicLong rAtomicLong=redisson.getAtomicLong(objectName);
        return rAtomicLong;
    }

    /**
     * get CountDownLatch
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public RCountDownLatch getRCountDownLatch(RedissonClient redisson, String objectName){
        RCountDownLatch rCountDownLatch=redisson.getCountDownLatch(objectName);
        return rCountDownLatch;
    }

    /**
     * get Topic
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public <M> RTopic<M> getRTopic(RedissonClient redisson, String objectName){
        RTopic<M> rTopic=redisson.getTopic(objectName);
        return rTopic;
    }


}
