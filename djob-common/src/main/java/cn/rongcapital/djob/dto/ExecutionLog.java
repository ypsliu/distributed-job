package cn.rongcapital.djob.dto;

import java.util.Date;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
public class ExecutionLog {

	private String _id;
	
	private String triggerGroup;
	
	private String triggerName;
	
	private String executor;
	
	private String clientId;
	
	private Date startTime;
	
	private Date completeTime;
	
	private String failureCause;
	
	private boolean isSuccess;

	/**
	 *
	 * @return
	 */
	public String getId() {
		return _id;
	}

	public void setId(String _id) {
		this._id = _id;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public String getFailureCause() {
		return failureCause;
	}

	public void setFailureCause(String failureCause) {
		this.failureCause = failureCause;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	@Override
	public String toString() {
		return "ExecutionLog [_id=" + _id + ", triggerGroup=" + triggerGroup + ", triggerName=" + triggerName
				+ ", executor=" + executor + ", clientId=" + clientId + ", startTime=" + startTime + ", completeTime="
				+ completeTime + ", failureCause=" + failureCause + ", isSuccess=" + isSuccess + "]";
	}
}
