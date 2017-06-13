package examples.pingpong;

import javax.ejb.Stateful;

import model.ACLMessage;
import model.AID;
import model.Agent;

@Stateful
public class Pong extends Agent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Pong() {
		super();
	}
	
	@Override
	public void handleMessage(ACLMessage message) {
		super.handleMessage(message);
		
		System.out.println("Ping  "+message.getSender().getName()+" send "+
				message.getPerformative()+" message:"+ message.getContent());
		AID pingId = message.getReplyTo();
        ACLMessage pongMessage = new ACLMessage();
        pongMessage.setPerformative(ACLMessage.Performative.INFORM);
        pongMessage.setSender(pingId);
        pongMessage.getRecivers().add(pingId);
        pongMessage.setContent("Cao ping");
        System.out.println("Pong "+pongMessage.getSender().getName()+" response with "+
        		pongMessage.getPerformative()+" message:" + pongMessage.getContent());
	}
}
