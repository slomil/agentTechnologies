package examples.pingpong;

import javax.ejb.Stateful;

import model.ACLMessage;
import model.AID;
import model.Agent;

@Stateful
public class Ping extends Agent {

	private ACLMessage pingMessage;
	
	public Ping() {
		super();
	}
	
	@Override
	public void handleMessage(ACLMessage message) {
		super.handleMessage(message);
		
		System.out.println("Ping "+message.getSender().getName()+" sends REQUEST message");
		AID pongId = message.getReplyTo();
        pingMessage = new ACLMessage();
        pingMessage.setPerformative(ACLMessage.Performative.REQUEST);
        pingMessage.setSender(pongId);
        pingMessage.getRecivers().add(pongId);
        pingMessage.setContent(" Caocao PONG :) ");
        
	}
	
	public void setContent(String content){
		pingMessage.setContent("Hello Pong! How are you?");
	}
}
