package cn.rongcapital.djob.loadbalance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class RoundRobinLoadBalancer extends AbstractJobLoadBalancer {
	
	private AtomicInteger sequence = new AtomicInteger();

	@Override
	public <T> T doSelect(List<T> clients) {
		return clients.get(sequence.getAndIncrement()%clients.size());
	}

}
