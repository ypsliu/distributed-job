package cn.rongcapital.djob.loadbalance;

import java.util.List;
import java.util.Random;

public class RandomLoadBalancer extends AbstractJobLoadBalancer {
	private final Random random = new Random();

	@Override
	public <T> T doSelect(List<T> clients) {
		return clients.get(random.nextInt(clients.size()));
	}

}
