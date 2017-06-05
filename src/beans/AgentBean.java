package beans;

import java.util.ArrayList;

import javax.ejb.Singleton;

import model.AID;
import model.Agent;
import model.AgentType;

@Singleton
public class AgentBean {
	
	//private ArrayList<Agent> allAgents = new ArrayList<>();
	private ArrayList<Agent> runningAgents = new ArrayList<>();
	private ArrayList<AgentType> allTypes = new ArrayList<>();
	
	/*public ArrayList<Agent> getAllAgents() {
		return allAgents;
	}
	public void setAllAgents(ArrayList<Agent> allAgents) {
		this.allAgents = allAgents;
	}*/
	public ArrayList<Agent> getRunningAgents() {
		return runningAgents;
	}
	public void setRunningAgents(ArrayList<Agent> runningAgents) {
		this.runningAgents = runningAgents;
	}
	public ArrayList<AgentType> getAllTypes() {
		return allTypes;
	}
	public void setAllTypes(ArrayList<AgentType> allTypes) {
		this.allTypes = allTypes;
	}
	
	
	
	/*public void addAgent(Agent agent){
		allAgents.add(agent);
	}
	public void removeAgent(Agent agent){
		allAgents.remove(agent);
	}
	public Agent getAgent(AID id){
		
		for(int i=0; i<allAgents.size(); i++){
			if(allAgents.get(i).getId().equals(id))
				return allAgents.get(i);
		}
		return null;
	}*/
	
	public void addRunningAgent(Agent agent){
		runningAgents.add(agent);
	}
	public void removeRunningAgent(Agent agent){
		runningAgents.remove(agent);
	}
	public Agent getRunningAgent(AID id){
		
		for(int i=0; i<runningAgents.size(); i++){
			if(runningAgents.get(i).getId().equals(id))
				return runningAgents.get(i);
		}
		return null;
	}
	
	public void addRunningAgents(ArrayList<Agent> agents){
		for(int i=0; i<agents.size(); i++)
			addRunningAgent(agents.get(i));
	}
	
	public void removeRunningAgents(ArrayList<Agent> agents){
		for(int i=0; i<agents.size(); i++)
			removeRunningAgent(agents.get(i));
	}
	
	public void addType(AgentType agentT){
		allTypes.add(agentT);
	}
	public void removeType(AgentType agentT){
		for(int i=0; i<allTypes.size(); i++){
			if(allTypes.get(i).getName().equals(agentT.getName())){
				allTypes.remove(i);
				break;
			}
		}
	}
	
	public void addAllTypes(ArrayList<AgentType> types){
		for(int i=0; i<types.size(); i++)
			addType(types.get(i));
	}
	
	public void removeAllTypes(ArrayList<AgentType> types){
		for(int i=0; i<types.size(); i++)
			removeType(types.get(i));
	}
	
}
