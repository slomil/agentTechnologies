package beans;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Singleton;
import javax.websocket.Session;

@Singleton
public class SessionBean {
	
	private HashMap<String, Session> sessions = new HashMap<String, Session>();
	
	public Session getSession(String address){
		return sessions.get(address);
	}
	
	public void addSession(Session session, String address){
		sessions.put(address, session);
	}
	
	public void removeSession(String address){
		sessions.remove(address);
	}
	
	public void removeAll(){
		for(String sess : sessions.keySet()){
			sessions.remove(sess);
		}
	}
	
	public ArrayList<Session> getAllSessions(){
		ArrayList<Session> temp = new ArrayList<Session>();
		for(Session sess : sessions.values()){
			temp.add(sess);
		}
		return temp;
	}

}
