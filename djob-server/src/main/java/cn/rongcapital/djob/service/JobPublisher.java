/**
 * 
 */
package cn.rongcapital.djob.service;

import cn.rongcapital.djob.dto.JobContext;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
public interface JobPublisher {

	void fired(JobContext context);
}
