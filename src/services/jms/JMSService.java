package services.jms;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.ACLMessage;

@Stateless
public class JMSService {

	@Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;
	@Resource(mappedName = "java:/jms/queue/mojQueue")
    private Queue queue;
	
	public void acceptMessage(ACLMessage message){
		
		delegateMessage(message);		
	}
	
	public void delegateMessage(ACLMessage message){
		try {
			Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer sender = session.createProducer(queue);
            
            ObjectMessage objectMessage = session.createObjectMessage();
            objectMessage.setObject(message);
            sender.send(objectMessage);
            
            sender.close();
            session.close();
            connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
