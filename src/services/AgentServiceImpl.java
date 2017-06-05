package services;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import beans.AgentBean;
import beans.ClusterBean;
import model.Agent;
import model.AgentType;

@Stateless
public class AgentServiceImpl implements AgentService {

	@EJB
	private AgentBean bean;
	@EJB
	private ClusterBean clusterBean;
	
	public AgentServiceImpl() {
		
	}
	
	@Override
	public Response add(ArrayList<AgentType> types) {
		
		try{
			bean.addAllTypes(types);
			return Response.status(Response.Status.OK).build();
		}catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@Override
	public Response delete(ArrayList<AgentType> types) {
		
		try{
			bean.removeAllTypes(types);
			return Response.status(Response.Status.OK).build();
		}catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	
	@Override
	public ArrayList<AgentType> getAgentTypes() {
		return bean.getAllTypes();
	}

	@Override
	public ArrayList<Agent> getRunningAgents() {
		return bean.getRunningAgents();
	}
	

	@Override
	public Response addRunningAgents(ArrayList<Agent> agents) {
		
		try{
			bean.addRunningAgents(agents);
			return Response.status(Response.Status.OK).build();
		}catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}

	@Override
	public Response startAgent(String type, String alias) {
		try{
			return Response.status(Response.Status.OK).build();
		}catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@Override
	public Response stopAgent(String aid) {
		try{
			return Response.status(Response.Status.OK).build();
		}catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	



}
