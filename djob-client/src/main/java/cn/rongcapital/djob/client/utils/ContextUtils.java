package cn.rongcapital.djob.client.utils;

import cn.rongcapital.djob.client.function.JobExecutor;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * Created by lilupeng on 11/04/2017
 *
 */
public class ContextUtils {

    private static ApplicationContext context;

    /**
     * Singleton
     *
     */
    private ContextUtils() {
    }

    /**
     *
     * @return
     */
    public static ApplicationContext getContext() {
        return context;
    }

    /**
     *
     * @param ctx
     */
    public static void setContext(ApplicationContext ctx) {
        context = ctx;
    }

    /**
     *
     * @param name
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> t) {
        return context.getBean(name, t);
    }

    /**
     *
     * @param className
     * @return
     */
    public static JobExecutor findExecutor(String className) {
        Map<String, JobExecutor> beans = getContext().getBeansOfType(JobExecutor.class);
        if (beans != null) {
            for (JobExecutor executor : beans.values()) {
                if (executor.getClass().getName().equals(className)) {
                    return executor;
                }
            }
        }

        return null;
    }
}
