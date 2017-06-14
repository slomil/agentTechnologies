package ws;

import model.AgentType;

public class WSRequest {
	
	private String request;
	private String host;
	private String agentType;
	private String name;
	
	public WSRequest() {
		// TODO Auto-generated constructor stub
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	

}
