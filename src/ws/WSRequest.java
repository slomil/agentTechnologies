package ws;

import model.AgentType;

public class WSRequest {
	
	private String request;
	private String address;
	private String agentType;
	private String name;
	
	public WSRequest() {
		// TODO Auto-generated constructor stub
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
