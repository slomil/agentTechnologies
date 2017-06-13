package examples.pingpong;

import javax.ejb.Stateful;

import model.ACLMessage;
import model.AID;
import model.Agent;

@Stateful
public class Ping extends Agent {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Ping() {
		super();
	}
	
	@Override
	public void handleMessage(ACLMessage message) {
		super.handleMessage(message);
		
		AID pongId = message.getReplyTo();
        ACLMessage pingMessage = new ACLMessage();
        pingMessage.setPerformative(ACLMessage.Performative.REQUEST);
        pingMessage.setSender(pongId);
        pingMessage.getRecivers().add(pongId);
        pingMessage.setContent(" Cao pong ");
        
	}
}
