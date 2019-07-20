/**
 * 
 */
package cn.rongcapital.djob.dto;

import cn.rongcapital.djob.enums.TriggerType;

import java.util.Date;

/**
 * @author shangchunming
 *
 */
public class Trigger extends BaseDto {

	private String desc;

	private TriggerType type;

	private String cron;

	private Date startTime = null;

	private Date endTime = null;

	private Date nextFireTime = null;

	private Date preFireTime = null;

	private Key jobKey;
	
//	@NotEmpty
	private String executor;
	
	private String data;
	
	private String callBackUrl;

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the type
	 */
	public TriggerType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(TriggerType type) {
		this.type = type;
	}

	/**
	 * @return the cron
	 */
	public String getCron() {
		return cron;
	}

	/**
	 * @param cron
	 *            the cron to set
	 */
	public void setCron(String cron) {
		this.cron = cron;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the nextFireTime
	 */
	public Date getNextFireTime() {
		return nextFireTime;
	}

	/**
	 * @param nextFireTime
	 *            the nextFireTime to set
	 */
	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	/**
	 * @return the preFireTime
	 */
	public Date getPreFireTime() {
		return preFireTime;
	}

	/**
	 * @param preFireTime
	 *            the preFireTime to set
	 */
	public void setPreFireTime(Date preFireTime) {
		this.preFireTime = preFireTime;
	}

	/**
	 * @return the jobKey
	 */
	public Key getJobKey() {
		return jobKey;
	}

	/**
	 * @param jobKey
	 *            the jobKey to set
	 */
	public void setJobKey(Key jobKey) {
		this.jobKey = jobKey;
	}
	
	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	@Override
	public String toString() {
		return "Trigger [desc=" + desc + ", type=" + type + ", cron=" + cron + ", startTime=" + startTime + ", endTime="
				+ endTime + ", nextFireTime=" + nextFireTime + ", preFireTime=" + preFireTime + ", jobKey=" + jobKey
				+ ", executor=" + executor + ", data=" + data + ", callBackUrl=" + callBackUrl + "]";
	}


}
