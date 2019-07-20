package cn.rongcapital.djob.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.rongcapital.djob.dto.Key;
import cn.rongcapital.djob.dto.Trigger;
import cn.rongcapital.djob.service.JobManager;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
@Controller
public class ManagerResourceImpl implements ManagerResource {
	@Autowired
	private JobManager jobManager;
	
	@Override
	public Trigger createTrigger(Trigger trigger) {
		return jobManager.createTrigger(trigger);
	}
	
	@Override
	public void removeTrigger(String group,String name) {
		jobManager.removeTrigger(new Key(name,group));
	}
	

	@Override
	public void updateTrigger(Trigger trigger) {
		jobManager.updateTrigger(trigger);
	}
	
	@Override
	public Trigger getTrigger(String group,String name) {
		return jobManager.getTrigger(new Key(name,group));
	}
	

	@Override
	public List<String> listTriggerGroups() {
		return jobManager.listTriggerGroups();
	}

	@Override
	public List<Trigger> listTriggers(String group) {
		return jobManager.listTriggers(group);
	}

	



}
