package services;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import model.ACLMessage;
import model.ACLMessage.Performative;

@Stateless
public class MessageServiceImpl implements MessageService{

	@Override
	public Response send(ACLMessage message) {
		
		try{
			return Response.status(Response.Status.OK).build();
		}catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@Override
	public ArrayList<Performative> getPerformatives() {
		return null;
	}

}
