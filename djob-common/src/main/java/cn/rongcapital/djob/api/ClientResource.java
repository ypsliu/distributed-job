/**
 * 
 */
package cn.rongcapital.djob.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * @author sunxin
 *
 */
@Path("/client")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public interface ClientResource {

	@Path("/")
	@POST
	void register(String clientId,long timestamp);

	@Path("/")
	@DELETE
	void unregister(String clientId);

	@Path("/")
	@PUT
	void heartbeat(String clientId,long timestamp);


}
