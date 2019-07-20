package cn.rongcapital.djob.loadbalance;

import java.util.List;

import cn.rongcapital.djob.service.JobLoadBalancer;

public abstract class AbstractJobLoadBalancer implements JobLoadBalancer {

	@Override
	public <T> T select(List<T> clients) {
		if(clients==null || clients.size()==0){
			return null;
		}
		if(clients.size()==1){
			return clients.get(0);
		}
		return doSelect(clients);
	}
	
	public abstract <T> T doSelect(List<T> clients);

}
