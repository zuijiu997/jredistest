import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Test {
    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getJedis();
        jedis.psubscribe(new KeyExpiredListener(), "__keyevent@0__:expired");
    }

    static class KeyExpiredListener extends JedisPubSub {

        @Override
        public void onPSubscribe(String pattern, int subscribedChannels) {
            System.out.println("onPSubscribe "
                    + pattern + " " + subscribedChannels);
        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {

            System.out.println("onPMessage pattern "
                    + pattern + " " + channel + " " + message);
        }



    }
}
