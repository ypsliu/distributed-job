/**
 * 
 */
package cn.rongcapital.djob.service;

import java.util.List;

import cn.rongcapital.djob.dto.Key;
import cn.rongcapital.djob.dto.Trigger;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
public interface JobManager{

	/**
	 *
	 * @param key
	 * @return
	 */
	Trigger getTrigger(Key key);

	/**
	 * to create the trigger
	 * 
	 * @param trigger
	 *            the creating trigger
	 * @return the created trigger
	 */
	Trigger createTrigger(Trigger trigger);

	/**
	 * to update the trigger
	 * 
	 * @param trigger
	 *            the updating trigger
	 */
	boolean updateTrigger(Trigger trigger);

	/**
	 * to remove the trigger
	 * 
	 * @param key
	 *            the trigger key
	 */
	boolean removeTrigger(Key key);

	/**
	 * to list the groups
	 * 
	 * @return the groups
	 */
	List<String> listTriggerGroups();

	/**
	 * to list the triggers by group
	 * 
	 * @param group
	 *            the group
	 * @return the triggers
	 */
	List<Trigger> listTriggers(String group);

}
