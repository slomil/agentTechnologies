package examples.pingpong;

import javax.ejb.Stateful;

import model.ACLMessage;
import model.AID;
import model.Agent;

@Stateful
public class Pong extends Agent {

	private ACLMessage pongMessage;
	
	public Pong() {
		super();
	}
	
	@Override
	public void handleMessage(ACLMessage message) {
		super.handleMessage(message);
		
		System.out.println("Pong "+message.getSender().getName()+" recieve message:" 
													+ message.getContent());
		AID pingId = message.getReplyTo();
        ACLMessage pongMessage = new ACLMessage();
        pongMessage.setPerformative(ACLMessage.Performative.INFORM);
        pongMessage.setSender(pingId);
        pongMessage.getRecivers().add(pingId);
        pongMessage.setContent("Caocaoooo ");
        System.out.println("Pong response :" + pongMessage.getContent());
	}
}
