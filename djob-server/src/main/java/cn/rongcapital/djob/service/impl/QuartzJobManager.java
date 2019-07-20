package cn.rongcapital.djob.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.NotFoundException;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.djob.ScheduleJob;
import cn.rongcapital.djob.dto.Key;
import cn.rongcapital.djob.dto.Trigger;
import cn.rongcapital.djob.enums.TriggerType;
import cn.rongcapital.djob.exception.DjobSchedulerException;
import cn.rongcapital.djob.service.JobManager;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
@Service
public class QuartzJobManager implements JobManager {
	@Autowired
	private Scheduler scheduler;

	private static final Logger logger = LoggerFactory.getLogger(QuartzJobManager.class);
	public static final String JOB_KEY_SUFFIX = "_job";
	public static final String PROP_KEY_EXECUTOR = "executor";
	public static final String PROP_KEY_DATA = "data";
	public static final String PROP_KEY_CALLBACK = "callback";

	@Override
	public Trigger createTrigger(Trigger trigger) {
		if(isExistTrigger(trigger.getKey().getName(),trigger.getKey().getGroup())){
//			throw new DuplicateException("the trigger is already existed.");
			updateTrigger(trigger);
			return trigger;
		}
		JobDetail quartzJob = JobBuilder.newJob(ScheduleJob.class)
				.withIdentity(trigger.getKey().getName()+JOB_KEY_SUFFIX, trigger.getKey().getGroup()).build();
		
		TriggerBuilder<org.quartz.Trigger> triggerBuilder = TriggerBuilder.newTrigger()
				.withIdentity(trigger.getKey().getName(), trigger.getKey().getGroup());
		if(trigger.getStartTime()!=null){
			triggerBuilder = triggerBuilder.startAt(trigger.getStartTime());
		}else{
			triggerBuilder = triggerBuilder.startNow();
		}
		if(trigger.getEndTime()!=null){
			triggerBuilder = triggerBuilder.endAt(trigger.getEndTime());
		}
		CronTrigger quartzTrigger = triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(trigger.getCron())).build();
		quartzTrigger.getJobDataMap().put(PROP_KEY_EXECUTOR, trigger.getExecutor());
		quartzTrigger.getJobDataMap().put(PROP_KEY_DATA, trigger.getData());
		quartzTrigger.getJobDataMap().put(PROP_KEY_CALLBACK,trigger.getCallBackUrl());
		
		try{
			scheduler.scheduleJob(quartzJob, quartzTrigger);
		} catch (SchedulerException e) {
			throw new DjobSchedulerException("schedule job error:"+e.getMessage(), e);
		}
		return trigger;
	}
	

	@Override
	public boolean removeTrigger(Key key) {
		if(!isExistTrigger(key.getName(),key.getGroup())){
			throw new NotFoundException("the trigger is not existed.");
		}
		try {
			scheduler.unscheduleJob(new TriggerKey(key.getName(),key.getGroup()));
		} catch (SchedulerException e) {
			throw new DjobSchedulerException("unschedule job error:"+e.getMessage(), e);
		}
		return true;
	}
	

	@Override
	public boolean updateTrigger(Trigger trigger) {
		if(!isExistTrigger(trigger.getKey().getName(),trigger.getKey().getGroup())){
			throw new NotFoundException("the trigger is not existed.");
		}
		TriggerBuilder<org.quartz.Trigger> triggerBuilder = TriggerBuilder.newTrigger()
				.withIdentity(trigger.getKey().getName(), trigger.getKey().getGroup());
		if(trigger.getStartTime()!=null){
			triggerBuilder = triggerBuilder.startAt(trigger.getStartTime());
		}else{
			triggerBuilder = triggerBuilder.startNow();
		}
		if(trigger.getEndTime()!=null){
			triggerBuilder = triggerBuilder.endAt(trigger.getEndTime());
		}
		CronTrigger quartzTrigger = triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(trigger.getCron())).build();
		quartzTrigger.getJobDataMap().put(PROP_KEY_EXECUTOR, trigger.getExecutor());
		quartzTrigger.getJobDataMap().put(PROP_KEY_DATA, trigger.getData());
		quartzTrigger.getJobDataMap().put(PROP_KEY_CALLBACK,trigger.getCallBackUrl());
		try{
			scheduler.rescheduleJob(quartzTrigger.getKey(), quartzTrigger);
		} catch (SchedulerException e) {
			throw new DjobSchedulerException("reschedule job error:"+e.getMessage(), e);
		}
		return true;
		
	}

	@Override
	public Trigger getTrigger(Key key) {
		org.quartz.Trigger quartzTrigger = null;
		try {
			quartzTrigger = scheduler.getTrigger(new TriggerKey(key.getName(),key.getGroup()));
		} catch (SchedulerException e) {
			logger.error("get trigger error:"+e.getMessage(), e);
		}
		if(quartzTrigger == null){
			throw new NotFoundException("the trigger is not existed.");
		}
		
		Trigger trigger = assembleTrigger(quartzTrigger);
		return trigger;
	}

	@Override
	public List<String> listTriggerGroups() {
		List<String> groups = Collections.emptyList();
		try {
			groups = scheduler.getTriggerGroupNames();
		} catch (SchedulerException e) {
			logger.error("get trigger groups error:"+e.getMessage(), e);
		}
		return groups;
	}

	@Override
	public List<Trigger> listTriggers(String group) {
		List<Trigger> triggers = new ArrayList<>();
		try {
			for(TriggerKey triggerKey : scheduler.getTriggerKeys(GroupMatcher.groupEquals(group))){
				triggers.add(assembleTrigger(scheduler.getTrigger(triggerKey)));
			}
		} catch (SchedulerException e) {
			logger.error("get triggers error:"+e.getMessage(), e);
		}
		return triggers;
	}
	
	private boolean isExistTrigger(String name,String group){
		org.quartz.Trigger quartzTrigger = null;
		try {
			quartzTrigger = scheduler.getTrigger(new TriggerKey(name,group));
		} catch (SchedulerException e) {
			logger.error("get trigger error:"+e.getMessage(), e);
			return false;
		}
		
		return quartzTrigger != null;
	}
	
	private Trigger assembleTrigger(org.quartz.Trigger quartzTrigger) {
		if(!(quartzTrigger instanceof CronTriggerImpl)){
			throw new IllegalStateException("the trigger is not cron.class="+quartzTrigger.getClass());
		}
		CronTriggerImpl cronTrigger = (CronTriggerImpl)quartzTrigger;
		Trigger trigger = new Trigger();
		trigger.setKey(new Key(quartzTrigger.getKey().getName(),quartzTrigger.getKey().getGroup()));
		trigger.setDesc(quartzTrigger.getDescription());
		trigger.setType(TriggerType.CRON);
		trigger.setCron(cronTrigger.getCronExpression());
		trigger.setStartTime(quartzTrigger.getStartTime());
		trigger.setEndTime(quartzTrigger.getEndTime());
		trigger.setNextFireTime(quartzTrigger.getNextFireTime());
		trigger.setPreFireTime(quartzTrigger.getPreviousFireTime());
		trigger.setJobKey(new Key(quartzTrigger.getJobKey().getName(),quartzTrigger.getJobKey().getGroup()));
		trigger.setExecutor(quartzTrigger.getJobDataMap().getString(PROP_KEY_EXECUTOR));
		trigger.setData(quartzTrigger.getJobDataMap().getString(PROP_KEY_DATA));
		trigger.setCallBackUrl(quartzTrigger.getJobDataMap().getString(PROP_KEY_CALLBACK));
		return trigger;
	}
	
}
