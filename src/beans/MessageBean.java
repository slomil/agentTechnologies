package beans;

import java.util.ArrayList;

import javax.ejb.Singleton;

import model.ACLMessage;

@Singleton
public class MessageBean {

	ArrayList<ACLMessage> messages = new ArrayList<>();
	
	
}
