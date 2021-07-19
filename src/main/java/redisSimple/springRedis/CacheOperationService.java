package redisSimple.springRedis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * CacheOperationService
 *
 * Author: zirui
 * Date: 2021/7/19
 */
public class CacheOperationService {
    private RedisTemplate redisTemplate;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * get all key that match
     *
     * @param patterns patterns
     * @return key keyset
     */
    public Set getKeys(Object patterns) {
        try {
            return redisTemplate.keys(patterns);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * set expired time
     *
     * @param key  key
     * @param time time(S)
     * @return success or not
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * get expired time by key
     *
     * @param key key
     * @return time(S), permanent if return 0
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * key exists?
     *
     * @param key key
     * @return true exists?
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * del cache
     *
     * @param key key, one or more
     * @return del num
     */

    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }


    /**
     * get ordinary cache
     *
     * @param key key
     * @return Object value
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * put cache
     *
     * @param key   key
     * @param value value
     * @return true/false
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * put cache and set expired
     *
     * @param key   key
     * @param value value
     * @param time  time(S), permanent if time < 0
     * @return true/false
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue()
                        .set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * incr
     *
     * @param key   key
     * @param delta incr num
     * @return result after incr
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("incr delta must bigger than 0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * decr
     *
     * @param key   key
     * @param delta decr value
     * @return result after decr
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("decr delta must bigger than 0");
        }
        return redisTemplate
                .opsForValue().increment(key, -delta);
    }


    // --------------RedisTemplate Map --------------------
    /**
     * HashGet
     *
     * @param key   key not null
     * @param field field not null
     * @return value
     */
    public Object hget(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * get all key-value pairs that match key
     *
     * @param key key
     * @return map
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key key
     * @param map map
     * @return true/false
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet and set time
     *
     * @param key  key
     * @param map  map
     * @param time time(S)
     * @return true/false
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * put key-value pair to map, if not exists then create
     *
     * @param key   key
     * @param field field
     * @param value value
     * @return true/false
     */
    public boolean hset(String key, String field, Object value) {
        try {
            redisTemplate.opsForHash().put(key, field, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * put key-value pair to map, if not exists then create
     *
     * @param key   key
     * @param field field
     * @param value value
     * @param time  time(S) will cover the old one
     * @return true/false
     */
    public boolean hset(String key, String field, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, field, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * del value
     *
     * @param key   key not null
     * @param field fields not null
     */
    public void hdel(String key, Object... field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    /**
     * exists
     *
     * @param key   key not null
     * @param field value not null
     * @return true/false
     */
    public boolean hHasKey(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * hash incr, if not exists, create
     *
     * @param key   key
     * @param field field
     * @param by    incr num
     * @return result
     */
    public double hincr(String key, String field, double by) {
        return redisTemplate.opsForHash().increment(key, field, by);
    }

    /**
     * hash decr, if not exists, create
     *
     * @param key   key
     * @param field field
     * @param by    decr num (negative value)
     * @return result
     */
    public double hdecr(String key, String field, double by) {
        return redisTemplate.opsForHash().increment(key, field, -by);
    }


    // --------------RedisTemplate Set --------------------
    /**
     * get all by key
     *
     * @param key key
     * @return all
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * exists
     *
     * @param key   key
     * @param value value
     * @return true/false
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * set cache
     *
     * @param key    key
     * @param values values
     * @return success num
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * set with time
     *
     * @param key    key
     * @param time   time(S)
     * @param values values
     * @return success num
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0)
                expire(key, time);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * get len of set
     *
     * @param key key
     * @return len
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * del that value = value
     *
     * @param key    key
     * @param values values
     * @return del num
     */
    public long setRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    // --------------RedisTemplate list --------------------
    /**
     * get list content
     *
     * @param key   key
     * @param start start
     * @param end   end index
     * @return list
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get list size
     *
     * @param key key
     * @return size
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * get list by index
     *
     * @param key   key
     * @param index index
     * @return element
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * set list
     *
     * @param key   key
     * @param value value
     * @return true/false
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * set list with time
     *
     * @param key   key
     * @param value value
     * @param time  time(S)
     * @return true/false
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * update by index
     *
     * @param key   key
     * @param index index
     * @param value new value
     * @return true/false
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * del mutil value
     *
     * @param key   key
     * @param count del num
     * @param value value
     * @return del num
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
