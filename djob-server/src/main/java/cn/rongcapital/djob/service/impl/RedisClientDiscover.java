package cn.rongcapital.djob.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import cn.rongcapital.djob.service.ClientDiscover;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
@Service
public class RedisClientDiscover implements ClientDiscover{
	
    @Autowired
    private StringRedisTemplate redisTemplate;
    
	public static final String CLIENT_DISCOVER_MAP_KEY = "client_map";
	private static final Integer CLIENT_HEARTBEAT_UPDATE_INTERVAL = 1000;
	private static final Integer CLIENT_HEARTBEAT_TIMEOUT = 2*CLIENT_HEARTBEAT_UPDATE_INTERVAL;

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1,new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			Thread th = new Thread(r);
			th.setName("client-discover");
			return th;
		}
	});
    
    @Override
    public void start(){
    	executor.scheduleWithFixedDelay(new ClientDiscoverTask(), CLIENT_HEARTBEAT_UPDATE_INTERVAL, CLIENT_HEARTBEAT_UPDATE_INTERVAL, TimeUnit.MILLISECONDS);
    }
    
    @Override
    public void update(){
    	HashOperations<String, Object, Object> hashOper = redisTemplate.opsForHash();
    	
    	Map<Object,Object> clients = hashOper.entries(CLIENT_DISCOVER_MAP_KEY);
    	for(Entry<Object,Object> client : clients.entrySet()){
    		long ts = (long)client.getValue();
    		long now = System.currentTimeMillis();
    		if(now - ts > CLIENT_HEARTBEAT_TIMEOUT){
    			hashOper.delete(CLIENT_DISCOVER_MAP_KEY, client.getKey());
    		}
    	}
    }
    
	@Override
	public List<String> getClients() {
		HashOperations<String, Object, Object> hashOper = redisTemplate.opsForHash();
		Set<Object> set = hashOper.keys(CLIENT_DISCOVER_MAP_KEY);
		List<String> clients = new ArrayList<>(); 
		for(Object client : set){
			clients.add((String)client);
		}
		return clients;
	}
    
    @Override
	public void stop() {
		executor.shutdownNow();
	}
	
    public class ClientDiscoverTask implements Runnable{
		@Override
		public void run()  {
			update();
		}
    }

	@Override
	public void putClient(String clientId, long timestamp) {
		HashOperations<String, Object, Object> hashOper = redisTemplate.opsForHash();
		hashOper.put(CLIENT_DISCOVER_MAP_KEY, clientId, timestamp);
	}

	@Override
	public void removeClient(String clientId) {
		HashOperations<String, Object, Object> hashOper = redisTemplate.opsForHash();
		hashOper.delete(CLIENT_DISCOVER_MAP_KEY, clientId);
	}

}
