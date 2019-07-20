package cn.rongcapital.djob.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.rongcapital.djob.dto.ExecutionLog;
import cn.rongcapital.djob.dto.ExecutionLogCondition;
import cn.rongcapital.djob.service.JobMonitor;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
@Controller
public class MonitorResourceImpl implements MonitorResource {
	@Autowired
	private JobMonitor jobMonitor;

	@Override
	public List<ExecutionLog> search(ExecutionLogCondition cond) {
		return jobMonitor.search(cond);
	}

	@Override
	public void saveLog(ExecutionLog log) {
		jobMonitor.saveLog(log);
	}

	


}
