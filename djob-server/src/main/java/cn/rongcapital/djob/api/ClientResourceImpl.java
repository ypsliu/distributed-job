package cn.rongcapital.djob.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.rongcapital.djob.service.ClientDiscover;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
@Controller
public class ClientResourceImpl implements ClientResource{
    @Autowired
    private ClientDiscover clientDiscover;
    
	@Override
	public void register(String clientId, long timestamp) {
		clientDiscover.putClient(clientId, timestamp);
	}

	@Override
	public void unregister(String clientId) {
		clientDiscover.removeClient(clientId);
	}

	@Override
	public void heartbeat(String clientId, long timestamp) {
		clientDiscover.putClient(clientId, timestamp);
	}

}
