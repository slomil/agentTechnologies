package model;

import java.io.Serializable;

public class Agent implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AID id;

	public Agent(){
		
	}
	
	
	public Agent(AID id) {
		super();
		this.id = id;
	}

	public AID getId() {
		return id;
	}

	public void setId(AID id) {
		this.id = id;
	}
	
	
	public void handleMessage(ACLMessage message){
		
		ACLMessage.Performative perf = message.getPerformative();
		String agent = message.getSender().getName();
		switch (perf) {
		case ACCEPT_PROPOSAL:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case AGREE:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case CANCEL:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case CALL_FOR_PROPOSAL:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case CONFIRM:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case DISCONFIRM:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case FAILURE:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case INFORM:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case INFORM_IF:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case INFORM_REF:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case NOT_UNDERSTOOD:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case PROPOSE:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case QUERY_IF:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case QUERY_REF:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case REFUSE:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case REJECT_PROPOSAL:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case REQUEST:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case REQUEST_WHEN:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case REQUEST_WHENEVER:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case SUBSCRIBE:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case PROXY:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		case PROPAGATE:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		default:
			System.out.println(perf +" MESSAGE FROM "+ agent);
			break;
		}
	}
	
}
