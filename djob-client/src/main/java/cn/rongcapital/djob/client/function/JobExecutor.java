package cn.rongcapital.djob.client.function;

import cn.rongcapital.djob.dto.JobContext;

/**
 * @author shangchunming
 *
 */
public interface JobExecutor {

	void execute(JobContext context, CallBack callBack);

	default void callback(CallBack callBack) {
	    System.out.println("default_callback...");
		if (callBack != null) {
			callBack.deal();
		}
	}
}
