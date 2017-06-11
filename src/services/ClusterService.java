package services;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.AgentCenter;

@Path("/node")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ClusterService {
	
	@GET
	@Path("/alive")
	public AgentCenter getCluster();
	
	@POST
	@Path("/")
	public Response add(AgentCenter center);
	
	@POST
	@Path("/getCenters")
	public ArrayList<AgentCenter> getCenters();
	
	@DELETE
	@Path("/{alias}")
	public Response delete(@PathParam("alias") String alias);

}
