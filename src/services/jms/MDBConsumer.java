package services.jms;

import java.util.ArrayList;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import beans.AgentBean;
import model.ACLMessage;
import model.AID;
import model.Agent;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName= "destinationType",propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName= "destination",propertyValue = "jms/queue/mojQueue")
})
public class MDBConsumer implements MessageListener{

	@EJB
	private MessageHelper helper;
	
	@Override
	public void onMessage(Message arg0) {
		
		Object object;
		try {
			object = ((ObjectMessage)arg0).getObject();
			ACLMessage message = (ACLMessage)object;
			helper.sendMessage(message);
				
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
