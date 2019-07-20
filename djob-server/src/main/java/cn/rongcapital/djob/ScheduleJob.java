package cn.rongcapital.djob;

import java.util.UUID;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.rongcapital.djob.dto.ExecutionLog;
import cn.rongcapital.djob.dto.JobContext;
import cn.rongcapital.djob.dto.Key;
import cn.rongcapital.djob.service.JobMonitor;
import cn.rongcapital.djob.service.JobPublisher;
import cn.rongcapital.djob.service.impl.QuartzJobManager;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
public class ScheduleJob extends QuartzJobBean {
	@Autowired
	private JobPublisher jobPublisher;
	
	@Autowired
	private JobMonitor jobMonitor;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleJob.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		String executor = context.getMergedJobDataMap().getString(QuartzJobManager.PROP_KEY_EXECUTOR);
		String data = context.getMergedJobDataMap().getString(QuartzJobManager.PROP_KEY_DATA);
		String callback = context.getMergedJobDataMap().getString(QuartzJobManager.PROP_KEY_CALLBACK);

		//save execution log
		ExecutionLog log = new ExecutionLog();
		log.setId(UUID.randomUUID().toString());
        log.setTriggerGroup(context.getTrigger().getKey().getGroup());
        log.setTriggerName(context.getTrigger().getKey().getName());
        log.setExecutor(executor);
        jobMonitor.saveLog(log);
        
        
		JobContext jc = new JobContext();
		jc.setTriggerKey(new Key(context.getTrigger().getKey().getName(),context.getTrigger().getKey().getGroup()));
		jc.setExecutor(executor);
		jc.setCallBackUrl(callback);
		jc.setData(data);
        jc.setExecutionId(log.getId());
		jc.setFiredTime(context.getFireTime());
		jc.setNextFiredTime(context.getNextFireTime());

		
        LOGGER.debug("{} is scheduled...",jc.getExecutor());
		jobPublisher.fired(jc);
		LOGGER.info("{} is published...",jc.getExecutor());
	}


}
