/**
 * 
 */
package cn.rongcapital.djob.listener;

import cn.rongcapital.djob.dto.JobContext;

/**
 * @author shangchunming
 *
 */
public interface TriggerListener {

	/**
	 *
	 * @param context
	 */
	void fired(JobContext context);
}
