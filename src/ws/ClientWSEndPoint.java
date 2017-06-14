package ws;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONException;

import com.google.gson.Gson;

import beans.ClusterBean;
import beans.SessionBean;
import model.ACLMessage.Performative;
import model.Agent;
import model.AgentCenter;
import model.AgentType;
import services.AgentService;
import services.MessageService;
import services.SessionService;

@Stateful
@ServerEndpoint("/wsClient")
public class ClientWSEndPoint {

	private Session session;
	private String host;
	@EJB
	private AgentService service;
	@EJB
	private MessageService mservice;
	@EJB
	private SessionBean sessionBean;
	@EJB
	private ClusterBean cluster;
	
	@OnMessage
	public void onMessage(String message, Session sess) throws JSONException {
		
		Gson gson = new Gson();
		WSRequest wsReq = gson.fromJson(message, WSRequest.class);
		host = wsReq.getHost();
		session  = sessionBean.getSession(host);
		if(session == null){
			session = sess;
			sessionBean.addSession(sess, host);
			
		}else if(!session.isOpen()){
			sessionBean.removeSession(host);
			session = sess;
			sessionBean.addSession(sess, host);
		}
		
		WSResponse wsRes = new WSResponse();
		
		switch (wsReq.getRequest()) {
		case "getAgentTypes":
			ArrayList<AgentType> agents = service.getAgentTypes();
			String json = new Gson().toJson(agents);
			wsRes.setAction("getAgentTypes");
			wsRes.setRetVal(json);
			json =  new Gson().toJson(wsRes);
			sendResponse(json);
			break;
		case "startAgent":
			service.startAgent(wsReq.getAgentType(), wsReq.getName());
			wsRes.setAction("startAgent");
			wsRes.setRetVal("uspesno");
			json =  new Gson().toJson(wsRes);
			sendResponse(json);
			break;
		case "getAllRunningAgents":
			ArrayList<Agent> ragents = service.getRunningAgents();
			json = new Gson().toJson(ragents);
			wsRes.setAction("getAllRunningAgents");
			wsRes.setRetVal(json);
			json =  new Gson().toJson(wsRes);
			sendResponse(json);
			break;
		case "getPerformatives":
			Performative[] perf = (Performative[]) mservice.getPerformatives();
			json = new Gson().toJson(perf);
			wsRes.setAction("getPerformatives");
			wsRes.setRetVal(json);
			json =  new Gson().toJson(wsRes);
			sendResponse(json);
			break;
		default:
			break;
		}
	}
	
	public void sendResponse(String retVal){
		try {
			ArrayList<Session> sessions = sessionBean.getAllSessions();
			for(int i=0; i<sessions.size(); i++){
				if(sessions.get(i).isOpen())
					sessions.get(i).getBasicRemote().sendObject(retVal);
			}
			informClusters(retVal);
		} catch (IOException | EncodeException e) {
			e.printStackTrace();
		}
	}
	
	public void informClusters(String message){
		
		ArrayList<AgentCenter> clusters = cluster.getAllClusters();
		for(int i=0; i<clusters.size(); i++){
			String tempAddress = clusters.get(i).getAddress();
			if(!ClusterBean.getLocal().getAddress().equals(tempAddress)){
				SessionService serv = sessionEndPoint(tempAddress);
				serv.infromClient(message);
			}
		}
	}
	
	public SessionService sessionEndPoint(String host){
		
		Client client = ClientBuilder.newClient();
		String url = "http://"+host+"/AT2/rest";
        WebTarget target = client.target(url);
        ResteasyWebTarget rtarget = (ResteasyWebTarget)target;
        SessionService sessionService = rtarget.proxy(SessionService.class);
        return sessionService;
	}
	
}
