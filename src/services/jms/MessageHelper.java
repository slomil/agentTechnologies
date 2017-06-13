package services.jms;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import beans.AgentBean;
import model.ACLMessage;
import model.AID;
import model.Agent;

@Stateless
public class MessageHelper {
	
	@EJB
	private AgentBean agents;
	
	public void sendMessage(ACLMessage message){
		ArrayList<AID> receivers = message.getRecivers();
		
		for(int i=0; i<receivers.size(); i++){
			Agent agent = agents.getRunningAgent(receivers.get(i));
			agent.handleMessage(message);
		}
	}

}
