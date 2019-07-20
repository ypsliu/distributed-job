package cn.rongcapital.djob.dto;

import java.util.Date;

/**
 * @author shangchunming
 *
 */
public class JobContext {
	private Key triggerKey;
	
	private String executor;
	
	private String callBackUrl;

	private String data;

	private String clientId;
	
	private String executionId;
	

	private Date firedTime;

	private Date nextFiredTime;


	@Override
	public String toString() {
		return "JobContext [triggerKey=" + triggerKey + ", executor=" + executor + ", callBackUrl=" + callBackUrl
				+ ", data=" + data + ", clientId=" + clientId + ", executionId=" + executionId + ", firedTime="
				+ firedTime + ", nextFiredTime=" + nextFiredTime + "]";
	}

	/**
	 *
	 */
	public Date getNextFiredTime() {
		return nextFiredTime;
	}

	public void setNextFiredTime(Date nextFiredTime) {
		this.nextFiredTime = nextFiredTime;
	}

	public Key getTriggerKey() {
		return triggerKey;
	}

	public void setTriggerKey(Key triggerKey) {
		this.triggerKey = triggerKey;
	}

	public Date getFiredTime() {
		return firedTime;
	}

	public void setFiredTime(Date firedTime) {
		this.firedTime = firedTime;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	
}
