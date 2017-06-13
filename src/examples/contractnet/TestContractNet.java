package examples.contractnet;

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
import model.ACLMessage.Performative;
import model.AID;
import model.Agent;
import model.AgentCenter;
import model.AgentType;
import services.jms.MessageHelper;

@Stateless
@Path("/contractnet")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TestContractNet extends Agent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private AgentBean agents;
	@EJB
	private MessageHelper helper;

	public TestContractNet() {
		super();
	}
	
	public TestContractNet(AID aid) {
		// TODO Auto-generated constructor stub
		super(aid);
	}
	
	@POST
	@Override
	public void handleMessage(ACLMessage message) {
		super.handleMessage(message);
		
		AgentCenter host = new AgentCenter("master", "localhost:8080");
		AgentType type = new AgentType("Reducer", "Reducer");
		ContractNet master = new ContractNet(new AID("master", host, type));
		agents.addRunningAgent(master);
		ArrayList<AID> receivers = new ArrayList<AID>();
		
		for(int i=0; i<4; i++){
			String name = "slave"+ Integer.toString(i);
			ContractNet cnet = new ContractNet(new AID(name, host, type));
			agents.addRunningAgent(cnet);
			receivers.add(cnet.getId());
		}
		
		message.setSender(master.getId());
		message.setRecivers(receivers);
		message.setPerformative(Performative.CALL_FOR_PROPOSAL);
		message.setContent("PONUDA .. CENA ..");
		
		helper.sendMessage(message);
		
	}

}
