package services;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TimerService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import beans.ClusterBean;
import clustering.MasterConfigDelete;
import model.AgentCenter;
import model.AgentType;

@Stateless
public class HeartbeatProtocol{

	@EJB
	private ClusterBean bean;

	@Schedule(hour = "*", minute = "*/1", info = "every minute")
	public void checkClusters(){
		
		System.out.println("Miiicaaaaa");
		
		AgentCenter currentCenter = ClusterBean.getLocal();
		ArrayList<AgentCenter> centers = bean.getAllClusters();
		
		for(int i=0; i<centers.size(); i++){
			
			if(!currentCenter.getAlias().equals(centers.get(i).getAlias())){
				
				ClusterService service = clusterEndPoint(centers.get(i).getAddress());
				AgentCenter cluster = service.getCluster();
				if(cluster == null)
					cluster = service.getCluster();
				
				if(cluster == null){
					cleanCluster(centers.get(i));
				}
			}
				
		}
		
	}
	
	public void cleanCluster(AgentCenter center){
		
		AgentService service = agentEndPoint(center.getAddress());
		ArrayList<AgentType> types = service.getAgentTypes();
		MasterConfigDelete masterConfigDelete = new MasterConfigDelete();
		
		masterConfigDelete.deleteCluster(center.getAlias());
		masterConfigDelete.deleteTypes(types);
		masterConfigDelete.informOtherToDeleteCluster(center.getAddress(), center);
		masterConfigDelete.informOtherToDeleteTypes(center.getAddress(), types);
	}
	
	public ClusterService clusterEndPoint(String host){
		Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://"+host+"/AT2/rest/node");
        ResteasyWebTarget rtarget = (ResteasyWebTarget)target;
        ClusterService nodeService = rtarget.proxy(ClusterService.class);
        return nodeService;
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
