package cn.rongcapital.djob;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.springframework.stereotype.Component;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
@Component
@ApplicationPath("/")
public class ApiApplication extends Application {
}