package cn.rongcapital.djob.test;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.djob.dto.Key;
import cn.rongcapital.djob.dto.Trigger;
import cn.rongcapital.djob.util.HttpUtil;

public class JobTest {

	private static final String URL = "http://localhost:8080/trigger";
	
	@Test
	public void test() {
		Trigger trigger = new Trigger();
		trigger.setKey(new Key("testTrigger","testGroup"));
		trigger.setExecutor("testExecutor");
		trigger.setCron("0/5 * * * * ?");
		String result = HttpUtil.postJson(URL, JSON.toJSONString(trigger));
		System.out.println(result);
		
	}
	


}
