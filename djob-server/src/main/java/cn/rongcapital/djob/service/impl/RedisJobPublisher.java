package cn.rongcapital.djob.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.djob.dto.JobContext;
import cn.rongcapital.djob.service.ClientDiscover;
import cn.rongcapital.djob.service.JobLoadBalancer;
import cn.rongcapital.djob.service.JobPublisher;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
@Service
@PropertySource("file:${APP_HOME}/conf/${env}/jedis.properties")
public class RedisJobPublisher implements JobPublisher{
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    @Autowired
    private ClientDiscover clientDiscover;
    
    @Autowired
    private JobLoadBalancer jobLoadBalancer;
    
    @Value("${redis.jobchannel}")
    private String channel;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisJobPublisher.class);
    
	@Override
	public void fired(JobContext context) {
		String client = jobLoadBalancer.select(clientDiscover.getClients());
		if(client == null){
			LOGGER.error("no client can be selected...triggerKey="+context.getTriggerKey());
			return;
		}
		context.setClientId(client);
		redisTemplate.convertAndSend(channel+"."+client, JSON.toJSONString(context));
	}

}
