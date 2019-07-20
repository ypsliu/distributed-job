/**
 * 
 */
package cn.rongcapital.djob.service;

import java.util.List;

import cn.rongcapital.djob.dto.ExecutionLog;
import cn.rongcapital.djob.dto.ExecutionLogCondition;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
public interface JobMonitor {

	public void start();

	public void stop();
	
	public void update();
	
	public void saveLog(ExecutionLog log );
	
	public List<ExecutionLog> search(ExecutionLogCondition cond);
}
