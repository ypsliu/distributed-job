package cn.rongcapital.djob.service;

import java.util.List;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
public interface JobLoadBalancer {
	public <T> T select(List<T> clients);
}
