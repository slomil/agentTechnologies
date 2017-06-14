package database;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import model.AgentType;

@Singleton
public class AgentDatabase {

	private HashMap<String, ArrayList<AgentType>> database = new HashMap<String, ArrayList<AgentType>>();
	
	@PostConstruct
	public void addDatabase(){
		ArrayList<AgentType> masterList = new ArrayList<AgentType>();
		masterList.add(new AgentType("Ping","Ping"));
		masterList.add(new AgentType("Pong","Pong"));
		database.put("master", masterList);
		
		ArrayList<AgentType> slaveList1 = new ArrayList<AgentType>();
		slaveList1.add(new AgentType("Reducer","Reducer"));
		database.put("slave1", slaveList1);
		
	}

	public HashMap<String, ArrayList<AgentType>> getDatabase() {
		return database;
	}

	public void setDatabase(HashMap<String, ArrayList<AgentType>> database) {
		this.database = database;
	}
	
	public ArrayList<AgentType> getTypes(String node){
		
		ArrayList<AgentType> retVal = database.get(node);
		return retVal;
	}
	
}
