package services;

import java.util.ArrayList;

import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Agent;
import model.AgentType;

@Path("/agents")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AgentService {
	
	@POST
	@Path("/classes")
	public Response add(ArrayList<AgentType> types);
	
	@DELETE
	@Path("/classes")
	public Response delete(ArrayList<AgentType> types);
	
	@GET
	@Path("/classes")
	public ArrayList<AgentType> getAgentTypes();
	
	@GET
	@Path("/running")
	public ArrayList<Agent> getRunningAgents();
	
	@POST
	@Path("/running")
	public Response addRunningAgents(ArrayList<Agent> agent);
	
	@PUT
	@Path("/running/{type}/{name}")
	public Response startAgent(@PathParam("type") String type, @PathParam("alias") String alias);
	
	@DELETE
	@Path("/running/{aid}")
	public Response stopAgent(@PathParam("aid") String aid);
	
}
