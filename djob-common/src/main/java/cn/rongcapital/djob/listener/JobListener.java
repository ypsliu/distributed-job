/**
 * 
 */
package cn.rongcapital.djob.listener;

import cn.rongcapital.djob.dto.JobContext;

/**
 * @author shangchunming
 *
 */
public interface JobListener {

	void clientStarted(String clientId);

	void clientStopped(String clientId);

	void beforeExecution(String clientId, JobContext context);

	void executionFailed(String clientId, JobContext context, Throwable exception);

	void afterExecution(String clientId, JobContext context);
//
//	void setClientId();
//
//	default String getClientId() {
//		return Consts.clientId;
//	}
}
