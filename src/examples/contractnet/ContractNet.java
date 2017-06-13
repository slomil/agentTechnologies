package examples.contractnet;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.ACLMessage;
import model.ACLMessage.Performative;
import model.AID;
import model.Agent;
import services.jms.MessageHelper;

@Stateless
public class ContractNet extends Agent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ContractNet() {
		super();
	}
	public ContractNet(AID aid){
		super(aid);
	}
	
	@Override
	public void handleMessage(ACLMessage message) {
		super.handleMessage(message);
		
		Performative perform = message.getPerformative();
		InitialContext ctx = null;
		MessageHelper helper = null;
		try {
			ctx = new InitialContext();
			helper =  (MessageHelper)
					ctx.lookup("java:module/MessageHelper");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		switch (perform) {
		case CALL_FOR_PROPOSAL:
			System.out.println("Agent "+ this.id.getName() + " recieve "+ perform+ 
					" message from "+ message.getSender().getName());
			
			ACLMessage retMess = new ACLMessage();
			retMess.setSender(this.id);
			ArrayList<AID> receivers = new ArrayList<AID>();
			receivers.add(message.getSender());
			retMess.setRecivers(receivers);
			if(this.id.getName().equals("slave0")){
				retMess.setPerformative(Performative.REFUSE);
			}else{
				retMess.setPerformative(Performative.PROPOSE);
			}
			
			helper.sendMessage(retMess);
			break;
			
		case REFUSE:
			System.out.println("Agent "+ this.id.getName() + " recieve "+ perform+ 
					" message from "+ message.getSender().getName());
			
			System.out.println("FINISHED COMUNICATION");
			
			break;
		
		case PROPOSE:
			
			System.out.println("Agent "+ this.id.getName() + " recieve "+ perform+ 
					" message from "+ message.getSender().getName());
			
			retMess = new ACLMessage();
			retMess.setSender(this.id);
			receivers = new ArrayList<AID>();
			receivers.add(message.getSender());
			retMess.setRecivers(receivers);
			if(message.getSender().getName().equals("slave1")){
				retMess.setPerformative(Performative.REFUSE);
			}else{
				retMess.setPerformative(Performative.ACCEPT_PROPOSAL);
			}
			helper.sendMessage(retMess);
			break;
		case ACCEPT_PROPOSAL:
			System.out.println("Agent "+ this.id.getName() + " recieve "+ perform+ 
					" message from "+ message.getSender().getName());
			
			retMess = new ACLMessage();
			retMess.setSender(this.id);
			receivers = new ArrayList<AID>();
			receivers.add(message.getSender());
			retMess.setRecivers(receivers);
			retMess.setPerformative(Performative.INFORM);
			helper.sendMessage(retMess);
			break;
		
		case INFORM:
			System.out.println("Agent "+ this.id.getName() + " recieve "+ perform+ 
					" message from "+ message.getSender().getName());
			
			if(message.getContent()==null){
				retMess = new ACLMessage();
				retMess.setSender(this.id);
				receivers = new ArrayList<AID>();
				receivers.add(message.getSender());
				retMess.setRecivers(receivers);
				retMess.setPerformative(Performative.INFORM);
				retMess.setContent("FINISHED");
				helper.sendMessage(retMess);
			}else
				System.out.println("FINISHED COMUNICATION");
			break;
		default:
			break;
		}
	}
}
