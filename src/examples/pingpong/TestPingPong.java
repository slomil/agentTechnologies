package examples.pingpong;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.AgentBean;
import model.ACLMessage;
import model.AID;
import model.Agent;
import model.AgentCenter;
import model.AgentType;
import services.jms.MessageHelper;

@Stateless
@Path("/pingpong")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TestPingPong extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private AgentBean bean;
	@EJB
	private MessageHelper helper;
	
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
		bean.addRunningAgent(ping);
		Pong pong = new Pong();
		pong.setId(pongAid);
		bean.addRunningAgent(pong);
		ArrayList<AID> receivers = new ArrayList<>();
		receivers.add(pongAid);
		
		ACLMessage acl = new ACLMessage();
		acl.setPerformative(message.getPerformative());
		acl.setContent(message.getContent());
		acl.setSender(pingAid);
		acl.setReplyTo(pingAid);
		acl.setRecivers(receivers);
		helper.sendMessage(acl);
	}
}
