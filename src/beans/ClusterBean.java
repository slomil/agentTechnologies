package beans;

import java.util.ArrayList;

import javax.ejb.Singleton;

import model.AgentCenter;

@Singleton
public class ClusterBean {
	
	private static AgentCenter local;
	private ArrayList<AgentCenter> allClusters = new ArrayList<>();
	
	
	public static AgentCenter getLocal() {
		return local;
	}
	public static void setLocal(AgentCenter local) {
		ClusterBean.local = local;
	}
	public ArrayList<AgentCenter> getAllClusters() {
		return allClusters;
	}
	public void setAllClusters(ArrayList<AgentCenter> allClusters) {
		this.allClusters = allClusters;
	}
	
	public void addCluster(AgentCenter cluster){
		allClusters.add(cluster);
	}
	public void removeCluster(String alias){
		for(int i=0; i<allClusters.size(); i++){
			if(allClusters.get(i).getAlias().equals(alias))
				allClusters.remove(i);
		}
	}
	public AgentCenter getCluster(String alias){
		
		for(int i=0; i<allClusters.size(); i++){
			if(allClusters.get(i).getAlias().equals(alias))
				return allClusters.get(i);
		}
		return null;
	}
	
	public void addAllClusters(ArrayList<AgentCenter> centers){
		
		for(int i=0; i<centers.size(); i++)
			allClusters.add(centers.get(i));
	}
}
