package cn.rongcapital.djob.client.utils;

import cn.rongcapital.djob.client.JobClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lilupeng on 12/04/2017
 *
 */
public class JobUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobClient.class);
    private static ThreadPoolTaskExecutor executor;

    /**
     *
     * @return
     */
    public static ThreadPoolTaskExecutor getTaskExecutor() {
        if (executor == null) {
            executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(10);
            executor.setMaxPoolSize(100);
            executor.setQueueCapacity(50);
            executor.setAllowCoreThreadTimeOut(true);
            executor.setKeepAliveSeconds(120); //真是2分钟后可以释放出来
            executor.initialize();
        }

        return executor;
    }

    /**
     *
     * @param className
     * @param superType
     * @param <T>
     * @return
     */
    public static <T> T initialize(final String className, final Class<T> superType) {
        final Class< ? extends T> clazz;
        try {
            clazz = Class.forName(className).asSubclass(superType);
            return initialize(clazz);
        }
        catch (ClassNotFoundException e) {
            LOGGER.error(e.getCause().getLocalizedMessage());
        }

        return null;
    }

    /**
     *
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T initialize(final Class<T> clz) {
        final Constructor<T> constructor;
        try {
            constructor = clz.getDeclaredConstructor();

            // if private, make it accessible
            if (constructor != null && !constructor.isAccessible()) {
                constructor.setAccessible(true);
                return constructor.newInstance();
            }
        }
        catch (NoSuchMethodException e) {
            LOGGER.error(e.getCause().getLocalizedMessage());
        }
        catch (IllegalAccessException e) {
            LOGGER.error(e.getCause().getLocalizedMessage());
        }
        catch (InstantiationException e) {
            LOGGER.error(e.getCause().getLocalizedMessage());
        }
        catch (InvocationTargetException e) {
            LOGGER.error(e.getCause().getLocalizedMessage());
        }

        return null;
    }
}