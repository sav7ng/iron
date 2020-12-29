package run.aquan.iron.system.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import run.aquan.iron.system.exception.IronException;
import run.aquan.iron.system.utils.common.SerializableUtils;
import run.aquan.iron.system.utils.common.StringUtils;

import java.util.Set;

/**
 * @Class JedisUtils
 * @Description
 * @Author Saving
 * @Date 2020.7.19 22:23
 * @Version 1.0
 **/
@Component
public class JedisUtils {

    private static JedisPool jedisPool;

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        JedisUtils.jedisPool = jedisPool;
    }

    /**
     * 获取Jedis实例
     *
     * @param
     * @return redis.clients.jedis.Jedis
     */
    public static synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                return jedisPool.getResource();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new IronException("获取Jedis资源异常:" + e.getMessage());
        }
    }

    /**
     * 释放Jedis资源
     *
     * @param
     * @return void
     */
    public static void closePool() {
        try {
            jedisPool.close();
        } catch (Exception e) {
            throw new IronException("释放Jedis资源异常:" + e.getMessage());
        }
    }

    /**
     * 获取redis键值-object
     *
     * @param key
     * @return java.lang.Object
     */
    public static Object getObject(String key) {
        try (Jedis jedis = getJedis()) {
            byte[] bytes = jedis.get(key.getBytes());
            if (StringUtils.isNotNull(bytes)) {
                return SerializableUtils.unserializable(bytes);
            }
        }
        return null;
    }

    /**
     * 设置redis键值-object
     *
     * @param key   结果
     * @param value
     * @return java.lang.String
     */
    public static String setObject(String key, Object value) {
        try (Jedis jedis = getJedis()) {
            return jedis.set(key.getBytes(), SerializableUtils.serializable(value));
        }
    }

    /**
     * 设置redis键值-object-expiretime
     *
     * @param key
     * @param value
     * @param expiretime
     * @return java.lang.String
     */
    public static String setObject(String key, Object value, int expiretime) {
        try (Jedis jedis = getJedis()) {
            String result = jedis.set(key.getBytes(), SerializableUtils.serializable(value));
            if ("OK".equals(result)) {
                jedis.expire(key.getBytes(), expiretime);
            }
            return result;
        }
    }

    /**
     * 获取redis键值-Json
     *
     * @param key
     * @return java.lang.Object
     */
    public static String getJson(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.get(key);
        }
    }

    /**
     * 设置redis键值-Json
     *
     * @param key
     * @param value
     * @return java.lang.String
     */
    public static String setJson(String key, String value) {
        try (Jedis jedis = getJedis()) {
            return jedis.set(key, value);
        }
    }

    /**
     * 设置redis键值-Json-expiretime
     *
     * @param key
     * @param value
     * @param expiretime
     * @return java.lang.String
     */
    public static String setJson(String key, String value, int expiretime) {
        try (Jedis jedis = getJedis()) {
            String result = jedis.set(key, value);
            if ("OK".equals(result)) {
                jedis.expire(key, expiretime);
            }
            return result;
        }
    }

    /**
     * 删除key
     *
     * @param key
     * @return java.lang.Long
     */
    public static Long delKey(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.del(key.getBytes());
        }
    }

    /**
     * key是否存在
     *
     * @param key
     * @return java.lang.Boolean
     */
    public static Boolean exists(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.exists(key.getBytes());
        }
    }

    /**
     * 模糊查询获取key集合(keys的速度非常快，但在一个大的数据库中使用它仍然可能造成性能问题，生产不推荐使用)
     *
     * @param key
     * @return java.util.Set<java.lang.String>
     */
    public static Set<String> keysS(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.keys(key);
        }
    }

    /**
     * 模糊查询获取key集合(keys的速度非常快，但在一个大的数据库中使用它仍然可能造成性能问题，生产不推荐使用)
     *
     * @param key
     * @return java.util.Set<java.lang.String>
     */
    public static Set<byte[]> keysB(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.keys(key.getBytes());
        }
    }

    /**
     * 获取过期剩余时间
     *
     * @param key
     * @return java.lang.String
     */
    public static Long ttl(String key) {
        try (Jedis jedis = getJedis()) {
            Long result = jedis.ttl(key);
            return result;
        }
    }

}
