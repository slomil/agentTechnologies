package clustering;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import model.AgentCenter;
import model.AgentType;
import services.AgentService;
import services.ClusterService;

public class MasterConfigDelete {
	
	private static final String masterAddress = "localhost:8080";
	
	public Response deleteCluster(String alias){
		
		ClusterService masterCluster = clusterEndPoint(masterAddress);
		Response response =  masterCluster.delete(alias);		
		if(response.getStatus()!= 200)
			response =  masterCluster.delete(alias);
		
		return response;
	}
	
	public Response deleteTypes(ArrayList<AgentType> agentsType){
		
		AgentService masterAgents = agentEndPoint(masterAddress);
		Response response = masterAgents.delete(agentsType);		
		if(response.getStatus()!= 200)
			response = masterAgents.delete(agentsType);	
		
		return response;
	}
	
	public Response informOtherToDeleteCluster(String address, AgentCenter center){
		
		ClusterService masterCluster = clusterEndPoint(masterAddress);
		Response response = Response.status(Response.Status.OK).build();
		
		ArrayList<AgentCenter> centers = masterCluster.getCenters();
		for(int i=0; i<centers.size(); i++){
			String tempAddress = centers.get(i).getAddress();
			
			if(!(tempAddress.equals(address) || tempAddress.equals(masterAddress))){
				ClusterService tempCluster = clusterEndPoint(tempAddress);
				response = tempCluster.delete(center.getAlias());		
				
				if(response.getStatus()!= 200)
					response = tempCluster.delete(center.getAlias());
				
				if(response.getStatus()!=200)
					break;
			}
		}	
		return response;
	}
	
	public Response informOtherToDeleteTypes(String address, ArrayList<AgentType> agentsType){
		
		ClusterService masterCluster = clusterEndPoint(masterAddress);
		Response response = Response.status(Response.Status.OK).build();
		
		ArrayList<AgentCenter> centers = masterCluster.getCenters();						
		for(int i=0; i<centers.size(); i++){
			String tempAddress = centers.get(i).getAddress();
			if(!(tempAddress.equals(address) || tempAddress.equals(masterAddress))){
				AgentService tempAgents = agentEndPoint(tempAddress);
				
				response = tempAgents.delete(agentsType);		
				
				if(response.getStatus()!= 200)
					response = tempAgents.delete(agentsType);
				
				if(response.getStatus()!=200)
					break;
				
			}
		}	
		return response;
	}
	
	public ClusterService clusterEndPoint(String host){
		
		Client client = ClientBuilder.newClient();
		String url = "http://"+host+"/AT2/rest";
        WebTarget target = client.target(url);
        ResteasyWebTarget rtarget = (ResteasyWebTarget)target;
        ClusterService clusterService = rtarget.proxy(ClusterService.class);
        return clusterService;
	}
	
	public AgentService agentEndPoint(String host){
		Client client = ClientBuilder.newClient();
		String url = "http://"+host+"/AT2/rest";
        WebTarget target = client.target(url);
        ResteasyWebTarget rtarget = (ResteasyWebTarget)target;
        AgentService agentService = rtarget.proxy(AgentService.class);
        return agentService;
	}	
}
