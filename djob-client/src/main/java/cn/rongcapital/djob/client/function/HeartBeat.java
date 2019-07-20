package cn.rongcapital.djob.client.function;

import cn.rongcapital.djob.client.utils.DateUtils;
import cn.rongcapital.djob.client.utils.PropertyUtils;
import cn.rongcapital.djob.client.utils.RedisUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by lilupeng on 10/05/2017
 *
 */
public class HeartBeat {

    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    /**
     *
     */
    public static void heartbeat() {
        executor.scheduleAtFixedRate(
                new EchoServer(),
                0,
                5,
                TimeUnit.MINUTES);
    }

}

class EchoServer implements Runnable {
    @Override
    public void run() {
        RedisUtils.getJedisHeartbeat().set(PropertyUtils.getProperty().getClientId(), DateUtils.getDateString());
    }
}