/**
 * 
 */
package cn.rongcapital.djob.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.rongcapital.djob.dto.Trigger;


/**
 * @author shangchunming
 *
 */
@Path("/")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public interface ManagerResource {

	/**
	 *
	 * @param trigger
	 * @return
	 */
	@Path("/trigger")
	@POST
	Trigger createTrigger(/*@Valid */Trigger trigger);

	/**
	 *
	 * @param trigger
	 */
	@Path("/trigger")
	@PUT
	void updateTrigger(/*@Valid*/ Trigger trigger);

	/**
	 *
	 * @param group
	 * @param name
	 */
	@Path("/trigger/{group}/{name}")
	@DELETE
	void removeTrigger(@PathParam("group") String group, @PathParam("name") String name);

	/**
	 *
	 * @param group
	 * @param name
	 * @return
	 */
	@Path("/trigger/{group}/{name}")
	@GET
	Trigger getTrigger(@PathParam("group") String group, @PathParam("name") String name);

	/**
	 *
	 * @return
	 */
	@Path("/trigger")
	@GET
	List<String> listTriggerGroups();

	/**
	 *
	 * @param group
	 * @return
	 */
	@Path("/trigger/{group}")
	@GET
	List<Trigger> listTriggers(@PathParam("group") String group);

}
