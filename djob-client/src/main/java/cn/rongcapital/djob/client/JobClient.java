package cn.rongcapital.djob.client;

import cn.rongcapital.djob.client.function.HeartBeat;
import cn.rongcapital.djob.client.function.JobExecutor;
import cn.rongcapital.djob.client.function.JobTracer;
import cn.rongcapital.djob.client.utils.*;
import cn.rongcapital.djob.dto.JobContext;
import cn.rongcapital.djob.listener.TriggerListener;
import cn.rongcapital.djob.utils.IpUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import redis.clients.jedis.JedisPubSub;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by lilupeng on 19/04/2017
 *
 */
public class JobClient implements TriggerListener, ApplicationContextAware {

    @Autowired
    @Qualifier("djob.client.jobTracer")
    private JobTracer jobTracer;

    @Value("#{'${djob.redis.host:127.0.0.1}'}")
    private String redisHost;

    @Value("#{'${djob.redis.port:6379}'}")
    private String redisPort;

    @Value("#{'${djob.redis.topic:rc.djob.pubsub}'}")
    private String redisTopic;

    @Value("#{'${djob.redis.password}'}")
    private String redisPassword;

    private static final Logger LOGGER = LoggerFactory.getLogger(JobClient.class);

    /**
     *
     */
    public JobClient() {
    }

    /**
     * @return
     */
    private void setupSubscriber() {
        LOGGER.info("djob client initializing...");

        Runnable task = () -> {
            try {
                RedisUtils.getJedisPubsub().subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String jsonBody) {
                        JobContext contextParam = JSON.parseObject(jsonBody, JobContext.class);
                        Date nextFireTime = contextParam.getNextFiredTime();

                        jobTracer.beforeExecution(PropertyUtils.getProperty().getClientId(), contextParam); // trace here

                        if (nextFireTime != null) {
                            if (new Date().after(nextFireTime)) {
                                LOGGER.info("djob client ({}) initializing...", contextParam);
                                jobTracer.executionFailed(PropertyUtils.getProperty().getClientId(), contextParam, new Exception("next fire time is less than current timestamp"));
                                return; // misfired
                            }
                        }

                        List<String> params = new LinkedList<>();
                        params.add(contextParam.getTriggerKey().getGroup());
                        params.add(contextParam.getTriggerKey().getName());
                        params.add(DateUtils.getDateString(nextFireTime));
                        String redisKey = params.stream().collect(Collectors.joining("_"));

                        long value = RedisUtils.getJedisLockConn().setnx(redisKey, redisKey);
                        if (1L == value) {
                            RedisUtils.getJedisLockConn().expire(redisKey, 300);
                            fired(contextParam);
                        }
                    }

                    @Override
                    public void onUnsubscribe(String channel, int subscribedChannels) {
                    }
                    @Override
                    public void onSubscribe(String channel, int subscribedChannels) {
                    }
                    @Override
                    public void onPUnsubscribe(String pattern, int subscribedChannels) {
                    }
                    @Override
                    public void onPSubscribe(String pattern, int subscribedChannels) {
                    }
                    @Override
                    public void onPMessage(String pattern, String channel, String message) {
                    }
                }, redisTopic);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(task).start();
    }

    /**
     *
     * @param context
     */
    @Override
    public void fired(final JobContext context) {
        final JobExecutor jobExecutor = ContextUtils.findExecutor(context.getExecutor());
        if (jobExecutor == null) {
            return;
        }
        JobUtils.getTaskExecutor().execute(() -> {
            jobExecutor.execute(context, () -> {
                jobTracer.afterExecution(PropertyUtils.getProperty().getClientId(), context);
            });
        });
    }

    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        init();
        setupSubscriber();
        ContextUtils.setContext(applicationContext);
    }

    /**
     *
     */
    public void init() {
        PropertyUtils.getProperty().setRedisPassword(redisPassword);
        PropertyUtils.getProperty().setRedisHost(redisHost);
        PropertyUtils.getProperty().setRedisPort(redisPort);
        PropertyUtils.getProperty().setRedisPassword(redisPassword);
        PropertyUtils.getProperty().setClientId(IpUtils.getIp() + UUID.randomUUID());

        RedisUtils.setupRedis();
        HeartBeat.heartbeat();
    }
}