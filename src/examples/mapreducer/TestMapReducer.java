package examples.mapreducer;

import java.io.File;
import java.net.URL;
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
@Path("/mapreducer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TestMapReducer extends Agent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private AgentBean agents;
	@EJB
	private MessageHelper helper;
	
	@POST
	@Override
	public void handleMessage(ACLMessage message) {
		super.handleMessage(message);

		URL u = getClass().getResource("examplesfiles");
		int numPaths = numPaths(u);
		
		AgentCenter host = new AgentCenter("master", "localhost:8080");
		AgentType type = new AgentType("Reducer", "Reducer");
		MapReducer master = new MapReducer(new AID("master", host, type));
		agents.addRunningAgent(master);
		ArrayList<AID> receivers = new ArrayList<AID>();
		
		for(int i=0; i<numPaths; i++){
			String name = "slave"+ Integer.toString(i);
			MapReducer reducer = new MapReducer(new AID(name, host, type));
			agents.addRunningAgent(reducer);
			receivers.add(reducer.getId());
		}
		
		message.setSender(master.getId());
		message.setRecivers(receivers);
		message.setPerformative(Performative.REQUEST);
		message.setContent(u.getPath());
		
		helper.sendMessage(message);
	}
	
	public int numPaths(URL u){
		
		File f = new File(u.getFile());
		File[] dir = f.listFiles();
		return dir.length;
	}
}
