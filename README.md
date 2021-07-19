# redisSimple

For learning purpose

Show how to use Jedis, Spring-data-redis RedisTemplate and SpringCache Annotations to operate Redis

Also use Redission to realize the redis distributed lock.



## Jedis

In **`src/test/java/redisSimple`**, you can find out the tester of Jedis.

The JedisPoolBuilderTest.java will show you how to build a JedisPool and how to pre-hot the pool.

Other files like HashDemo.java, StringDemo.java .... show the basic operations of redis by using Jedis.



## SpringRedis With RedisTemplate

In **`src/main/java/redisSimple/springRedis/CacheOperationService.java`**, demonstrates how 

to build redisTemplate and use it, the test case can be found in **`src/main/java/redisSimple/springRedis/UserServiceImplWithTemplate.java`** and 

`**src/test/java/redisSimple/springRedis/UserServiceImplWithTemplateTest.java**`



In **`src/main/java/redisSimple/springRedis/UserServiceImplInTemplate.java`**, shows how to operate redis by using in-built redistemplate



By using redistemplate, you should set the configuration file **`src/main/resources/redis.properties`** and **`src/main/resources/spring-redis.xml**`



## SpringRedis With Annotation

**`src/main/java/redisSimple/springRedis/UserServiceImplWithAnno.java`** give out the demo

test case in **`src/test/java/redisSimple/springRedis/UserServiceImplWithAnnoTest.java`**



## Distributed Lock

In `src/main/java/redisSimple/redisLock and src/test/java/redisSimple/redisLock`, shows how to use Redission to realize the distributed lock(Single Server)