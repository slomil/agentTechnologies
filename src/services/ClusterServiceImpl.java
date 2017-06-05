package services;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import beans.ClusterBean;
import model.AgentCenter;

@Stateless
public class ClusterServiceImpl implements ClusterService{

	@EJB
	private ClusterBean bean;
	
	public ClusterServiceImpl() {
	}
	
	@Override
	public AgentCenter getCluster() {
		return ClusterBean.getLocal();
	}
	
	@Override
	public Response add(AgentCenter center) {
		
		try{
			bean.addCluster(center);
			Response response = Response.status(Response.Status.OK).build();
			return response;
		}catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@Override
	public ArrayList<AgentCenter> getCenters() {
		return bean.getAllClusters();
	}

	@Override
	public Response delete(String alias) {
		
		try{
			bean.removeCluster(alias);
			return Response.status(Response.Status.OK).build();
		}catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	

}
