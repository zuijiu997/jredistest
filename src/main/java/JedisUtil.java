import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Set;

public class JedisUtil {

    private static JedisPool jedisPool = null;

    public static Jedis getJedis() {
        if (jedisPool == null) {
            String host = "127.0.0.1";
            String port = "6379";
            String password = null;
            GenericObjectPoolConfig poolConfig  = new GenericObjectPoolConfig();
            jedisPool = new JedisPool(poolConfig, host, Integer.parseInt(port),10000, password);
        }
        return jedisPool.getResource();
    }

    private static void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    public static Set<Tuple> zrevrangeWithScores(int dbIndex, String key, long start, long end) {
        Jedis jedis = getJedis();
        jedis.select(dbIndex);
        Set<Tuple> tuples = jedis.zrevrangeWithScores(key, start, end);
        closeJedis(jedis);
        return tuples;
    }

    public static Set<Tuple> zrangeWithScores(int dbIndex, String key, long start, long end) {
        Jedis jedis = getJedis();
        jedis.select(dbIndex);
        Set<Tuple> tuples = jedis.zrangeWithScores(key, start, end);
        closeJedis(jedis);
        return tuples;
    }

    public static Set<String> zrevrange(int dbIndex, String key, long start, long end) {
        Jedis jedis = getJedis();
        jedis.select(dbIndex);
        Set<String> set = jedis.zrevrange(key, start, end);
        closeJedis(jedis);
        return set;
    }

    public static List<String> lrange(int dbIndex, String key, long start, long end) {
        Jedis jedis = getJedis();
        jedis.select(dbIndex);
        List<String> getrange = jedis.lrange(key, start, end);
        closeJedis(jedis);
        return getrange;
    }

    public static String hget(int dbIndex, String key, String name) {
        Jedis jedis = getJedis();
        jedis.select(dbIndex);
        String hget = jedis.hget(key, name);
        closeJedis(jedis);
        return hget;
    }
}
