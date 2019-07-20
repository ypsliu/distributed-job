package cn.rongcapital.djob.client.function;

import cn.rongcapital.djob.client.utils.PropertyUtils;
import cn.rongcapital.djob.client.utils.RedisUtils;
import cn.rongcapital.djob.dto.ExecutionLog;
import cn.rongcapital.djob.dto.JobContext;
import cn.rongcapital.djob.listener.JobListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;

/**
 * Created by lilupeng on 09/05/2017
 *
 */
public class JobTracer implements JobListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobTracer.class);

    private final ThreadLocal<ExecutionLog> context = new ThreadLocal<>();

    /**
     *
     * @param clientId
     */
    @Override
    public void clientStarted(String clientId) {
    }

    /**
     *
     * @param clientId
     */
    @Override
    public void clientStopped(String clientId) {
        RedisUtils.getJedisPubsub().close(); // FIXME need close redis here?
        RedisUtils.getJedisLockConn().close();
        RedisUtils.getJedisTraceResource().close();
        RedisUtils.getJedisHeartbeat().close();
        PropertyUtils.getProperty().setClientId(null);
    }

    /**
     *
     * @param clientId
     * @param context
     */
    @Override
    public void beforeExecution(String clientId, JobContext context) {
        ExecutionLog log = new ExecutionLog();
        try {
            log.setId(UUID.randomUUID().toString());
            log.setTriggerGroup(context.getTriggerKey().getGroup());
            log.setTriggerName(context.getTriggerKey().getName());
            log.setExecutor(context.getExecutor());
            log.setClientId(clientId);
            log.setStartTime(new Date());

            this.context.set(log);

            RedisUtils.getJedisTraceResource().rpush(log.getId(), JSON.toJSONString(log));
        } catch (Exception e) {
            LOGGER.error("fail trace failed, log: {}, error: {}", log, e.getLocalizedMessage());
        }
    }

    /**
     *
     * @param clientId
     * @param context
     * @param exception
     */
    @Override
    public void executionFailed(String clientId, JobContext context, Throwable exception) {
        ExecutionLog log = this.context.get();
        try {
            if (log != null) {
                if (exception != null) {
                    log.setFailureCause(JSON.toJSONString(exception));
                }
                log.setCompleteTime(new Date());

                RedisUtils.getJedisTraceResource().rpush(log.getId(), JSON.toJSONString(log));
            }
        } catch (Exception e) {
            LOGGER.error("fail trace failed, log: {}, error: {}", log, e.getLocalizedMessage());
        } finally {
            if (log != null) {
                this.context.remove();
            }
        }
    }

    /**
     *
     * @param clientId
     * @param context
     */
    @Override
    public void afterExecution(String clientId, JobContext context) {
        LOGGER.info("after execution of client id {} with context {}", clientId, context);
        ExecutionLog log = this.context.get();
        try {
            if (log != null) {
                log.setSuccess(true);
                log.setCompleteTime(new Date());

                RedisUtils.getJedisTraceResource().rpush(log.getId(), JSON.toJSONString(log));
            }
        } catch (Exception e) {
            LOGGER.error("fail trace failed, log: {}, error: {}", log, e.getLocalizedMessage());
        } finally {
            if (log != null) {
                this.context.remove();
            }
        }
    }
}
