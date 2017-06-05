package clustering;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.AgentBean;
import beans.ClusterBean;
import database.AgentDatabase;
import model.Agent;
import model.AgentCenter;
import model.AgentType;

@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class ClusterStartup {
	
	//private static final String masterAlias = "master";
	private static final String masterAddress = "localhost:8080";
	private static AgentCenter center;
	private static ArrayList<AgentType> agentsType;
	
	@EJB
	private ClusterBean clusterBean;
	@EJB
	private AgentBean agentBean;
	@EJB
	private AgentDatabase database;
	
	@PostConstruct
	public void addCluster(){
		
		String address = "localhost:8080";
		String alias = "master";
		center = new AgentCenter(address, alias);
		
		center = check(center);
		address = center.getAddress();
		alias = center.getAlias();
		agentsType =  database.getTypes(alias);
		
		
		if(!center.getAddress().equals(masterAddress)){
			
			MasterConfigAdd masterConfigAdd = new MasterConfigAdd();
			MasterConfigDelete masterConfigDelete = new MasterConfigDelete();
			
			boolean cleaning = false;
			Response response = masterConfigAdd.addCluster(center);
			if(response.getStatus() != 200)
				cleaning = true;
			response = masterConfigAdd.addTypes(agentsType);
			if(response.getStatus() != 200)
				cleaning = true;
			response = masterConfigAdd.informOtherToAddCluster(address, center);
			if(response.getStatus() != 200)
				cleaning = true;
			response = masterConfigAdd.informOtherToAddTypes(address, agentsType);
			if(response.getStatus() != 200)
				cleaning = true;
			
			if(!cleaning){
				ClusterBean.setLocal(center);
				ArrayList<AgentCenter> centers = masterConfigAdd.clusterEndPoint(masterAddress).getCenters();
				clusterBean.addAllClusters(centers);		
				ArrayList<AgentType> tempTypes = masterConfigAdd.agentEndPoint(masterAddress).getAgentTypes();
				agentBean.addAllTypes(tempTypes);			
				ArrayList<Agent> runningAgents = masterConfigAdd.agentEndPoint(masterAddress).getRunningAgents();
				agentBean.addRunningAgents(runningAgents);	
			}else{
				
				masterConfigDelete.deleteCluster(center.getAlias());
				masterConfigDelete.deleteTypes(agentsType);
				masterConfigDelete.informOtherToDeleteCluster(address, center);
				masterConfigDelete.informOtherToDeleteTypes(address, agentsType);
			}
			
		}else{
			
			clusterBean.addCluster(center);
			agentBean.addAllTypes(agentsType);
			ClusterBean.setLocal(center);
		}
	}
	
	
	
	@PreDestroy
	public void removeCluster(){
		MasterConfigDelete masterConfigDelete = new MasterConfigDelete();
		
		masterConfigDelete.deleteCluster(center.getAlias());
		masterConfigDelete.deleteTypes(agentsType);
		masterConfigDelete.informOtherToDeleteCluster(center.getAddress(), center);
		masterConfigDelete.informOtherToDeleteTypes(center.getAddress(), agentsType);
		
	}
	
	public AgentCenter check(AgentCenter center){
		Client client = ClientBuilder.newClient();
		Response responseHosts = client.target("http://localhost:8080/AT2/rest/help/get").request(MediaType.TEXT_PLAIN).get();

		if(responseHosts.getStatus() != 404){
			center.setAddress("localhost:11080");
			center.setAlias("slave1");
		}
		
		return center;
	}
	

	
	
}
