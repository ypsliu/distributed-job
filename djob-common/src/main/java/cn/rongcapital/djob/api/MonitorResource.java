/**
 * 
 */
package cn.rongcapital.djob.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.rongcapital.djob.dto.ExecutionLog;
import cn.rongcapital.djob.dto.ExecutionLogCondition;

/**
 * @author shangchunming
 *
 */
@Path("/monitor")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public interface MonitorResource {

	
	@Path("/log/search")
	@POST
	List<ExecutionLog> search(ExecutionLogCondition cond);
	
	@Path("/log")
	@POST
	void saveLog(ExecutionLog log);

}
