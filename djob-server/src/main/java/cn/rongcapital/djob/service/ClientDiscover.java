package cn.rongcapital.djob.service;

import java.util.List;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
public interface ClientDiscover {
	public void start();

	public void update();
	
	public List<String> getClients();
	
	public void putClient(String clientId,long timestamp);
	
	public void removeClient(String clientId);

	public void stop();
}
