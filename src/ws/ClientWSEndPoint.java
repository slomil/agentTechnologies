package ws;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONException;

import com.google.gson.Gson;

import model.ACLMessage.Performative;
import model.Agent;
import model.AgentType;
import services.AgentService;
import services.MessageService;

@Stateful
@ServerEndpoint("/wsClient")
public class ClientWSEndPoint {

	private Session session;
	@EJB
	private AgentService service;
	@EJB
	private MessageService mservice;
	
	@OnMessage
	public void onMessage(String message, Session sess) throws JSONException {
		session = sess;
		
		Gson gson = new Gson();
		WSRequest wsReq = gson.fromJson(message, WSRequest.class);
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
			session.getBasicRemote().sendText(retVal);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
