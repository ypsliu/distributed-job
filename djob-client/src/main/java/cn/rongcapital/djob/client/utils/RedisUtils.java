package cn.rongcapital.djob.client.utils;

import cn.rongcapital.djob.client.Consts;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by lilupeng on 08/05/2017
 *
 */
public class RedisUtils {

    private static JedisPoolConfig jedisPoolConfig;
    private static JedisPool jedisPool;

    private static Jedis jedisPubsub;
    private static Jedis jedisLockConn;
    private static Jedis jedisTraceResource;
    private static Jedis jedisHeartbeat;

    /**
     *
     */
    public static void setupRedis() {
        JedisPoolConfig jedisPoolConfig = RedisUtils.getRedisConfig();
        if (PropertyUtils.getProperty().getRedisPassword() != null && !Consts.BLANK.equals(PropertyUtils.getProperty().getRedisPassword())) {
            JedisPool jedisQueue = new JedisPool(jedisPoolConfig,
                    PropertyUtils.getProperty().getRedisHost(),
                    Integer.valueOf(PropertyUtils.getProperty().getRedisPort()), 5000, PropertyUtils.getProperty().getRedisPassword());
            RedisUtils.setJedisPool(jedisQueue);
        }
        else {
            JedisPool jedisQueue = new JedisPool(jedisPoolConfig, PropertyUtils.getProperty().getRedisHost(), Integer.valueOf(PropertyUtils.getProperty().getRedisPort()));
            RedisUtils.setJedisPool(jedisQueue);
        }

        // redis resource
        RedisUtils.setJedisPubsub(RedisUtils.getJedisPool().getResource());
        RedisUtils.setJedisLockConn(RedisUtils.getJedisPool().getResource());
        RedisUtils.setJedisTraceResource(RedisUtils.getJedisPool().getResource());
        RedisUtils.setJedisHeartbeat(RedisUtils.getJedisPool().getResource());
    }

    /**
     *
     * @return
     */
    public static JedisPoolConfig getRedisConfig() {
        if (jedisPoolConfig != null) {
            return jedisPoolConfig;
        }

        jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(200);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setMaxWaitMillis(3000);

        return jedisPoolConfig;
    }

    /**
     *
     * @return
     */
    public static JedisPool getJedisPool() {
        return jedisPool;
    }

    /**
     *
     * @param jedisPool
     */
    public static void setJedisPool(JedisPool jedisPool) {
        RedisUtils.jedisPool = jedisPool;
    }

    /**
     *
     * @return
     */
    public static Jedis getJedisPubsub() {
        return jedisPubsub;
    }

    public static void setJedisPubsub(Jedis jedisPubsub) {
        RedisUtils.jedisPubsub = jedisPubsub;
    }

    public static Jedis getJedisLockConn() {
        return jedisLockConn;
    }

    public static void setJedisLockConn(Jedis jedisLockConn) {
        RedisUtils.jedisLockConn = jedisLockConn;
    }

    public static Jedis getJedisTraceResource() {
        return jedisTraceResource;
    }

    public static void setJedisTraceResource(Jedis jedisTraceResource) {
        RedisUtils.jedisTraceResource = jedisTraceResource;
    }

    public static Jedis getJedisHeartbeat() {
        return jedisHeartbeat;
    }

    public static void setJedisHeartbeat(Jedis jedisHeartbeat) {
        RedisUtils.jedisHeartbeat = jedisHeartbeat;
    }
}
