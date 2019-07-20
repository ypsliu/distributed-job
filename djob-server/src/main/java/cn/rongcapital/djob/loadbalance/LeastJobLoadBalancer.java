package cn.rongcapital.djob.loadbalance;

import java.util.List;

public class LeastJobLoadBalancer extends AbstractJobLoadBalancer {

	@Override
	public <T> T doSelect(List<T> clients) {
		//TODO: 选择job数最少的client
		return null;
	}

}
