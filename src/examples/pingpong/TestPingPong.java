package examples.pingpong;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.ACLMessage;
import model.AID;
import model.Agent;
import model.AgentCenter;
import model.AgentType;

@Stateless
@Path("/pingpong")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TestPingPong extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@POST
	@Override
	public void handleMessage(ACLMessage message) {
		super.handleMessage(message);
		
		AgentCenter host = new AgentCenter("localhost:8080", "master");
		AgentType type = new AgentType("Ping", "Ping");
		AID pingAid = new AID("mica", host, type);
		AgentType type2 = new AgentType("Pong", "Pong");
		AID pongAid = new AID("boba", host, type2);
		
		Ping ping = new Ping();
		ping.setId(pingAid);
		Pong pong = new Pong();
		pong.setId(pongAid);
		
		ACLMessage acl = new ACLMessage();
		acl.setReplyTo(pingAid);
		ping.handleMessage(acl);
		
	}
}
