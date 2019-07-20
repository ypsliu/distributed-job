package cn.rongcapital.djob.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.djob.dto.JobContext;
import cn.rongcapital.djob.service.ClientDiscover;
import cn.rongcapital.djob.service.JobLoadBalancer;
import cn.rongcapital.djob.service.JobPublisher;
import cn.rongcapital.djob.util.HttpUtil;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
public class HttpJobPublisher implements JobPublisher{
    @Autowired
    private ClientDiscover clientDiscover;
    
    @Autowired
    private JobLoadBalancer jobLoadBalancer;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpJobPublisher.class);
    
	@Override
	public void fired(JobContext context) {
		String client = jobLoadBalancer.select(clientDiscover.getClients());
		if(client == null){
			LOGGER.error("no client can be selected...triggerKey="+context.getTriggerKey());
			return;
		}
		if(context.getCallBackUrl()==null){
			LOGGER.error("no callback url is specified...triggerKey="+context.getTriggerKey());
			return;
		}
		context.setClientId(client);
		HttpUtil.postJson(context.getCallBackUrl(), JSON.toJSONString(context));
	}

}
