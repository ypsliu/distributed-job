package cn.rongcapital.djob.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
@Provider
@Service
public class TraceWebRequestInterceptor implements ContainerRequestFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(TraceWebRequestInterceptor.class);

	@Context
	private HttpServletRequest httpRequest;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		final String path = requestContext.getUriInfo().getPath();
		
		LOGGER.info(path+":"+httpRequest.getQueryString());
	}

}
