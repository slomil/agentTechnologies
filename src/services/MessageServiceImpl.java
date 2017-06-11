package services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import jms.JMSService;
import model.ACLMessage;

@Stateless
public class MessageServiceImpl implements MessageService{

	@EJB
	private JMSService jms;
	
	@Override
	public Response send(ACLMessage message) {
		
		try{
			jms.acceptMessage(message);
			return Response.status(Response.Status.OK).build();
		}catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@Override
	public Object[] getPerformatives() {
		Object [] retVal = null;
		try {
			retVal = ACLMessage.class.newInstance().getAllPerformatives();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retVal;
	}

}
