package jms;

import java.util.ArrayList;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import model.ACLMessage;
import model.AID;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName= "destinationType",propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName= "destination",propertyValue = "jms/queue/mojQueue")
})
public class MDBConsumer implements MessageListener{

	@Override
	public void onMessage(Message arg0) {
		
		Object object;
		try {
			object = ((ObjectMessage)arg0).getObject();
			ACLMessage message = (ACLMessage)object;
			
			ArrayList<AID> receivers = message.getRecivers();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
