package cn.rongcapital.djob;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import cn.rongcapital.djob.service.ClientDiscover;
import cn.rongcapital.djob.service.JobMonitor;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class,MongoDataAutoConfiguration.class})
@PropertySources({ @PropertySource(value = "file:${APP_HOME}/conf/${env}/datasource.properties"), 
		@PropertySource(value = "file:${APP_HOME}/conf/${env}/mongodb.properties")})
//@ImportResource({"classpath:/datasource.xml", "classpath:/quartz.xml","classpath:/jedis.xml"})
@ImportResource({"classpath:/datasource.xml", "classpath:/quartz.xml","classpath:/jedis.xml","classpath:/mongodb.xml"})
public class JobServer {
	public static void main(String[] args) {
		// logback.configurationFile
		System.setProperty("logging.config", System.getProperty("APP_HOME") + File.separator + "conf" + File.separator
				+ System.getProperty("env") + File.separator + "logback.xml");
		
		ApplicationContext context = SpringApplication.run(JobServer.class, args);
		
		// monitor
		JobMonitor monitor = context.getBean(JobMonitor.class);
		monitor.start();
		
		// discover
		ClientDiscover discover = context.getBean(ClientDiscover.class);
		discover.start();
		
	}
}
