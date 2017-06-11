package services;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONObject;

import beans.AgentBean;
import beans.ClusterBean;
import model.AID;
import model.Agent;
import model.AgentCenter;
import model.AgentType;

@Stateless
public class AgentServiceImpl implements AgentService {

	@EJB
	private AgentBean bean;
	@EJB
	private ClusterBean clusterBean;
	
	public AgentServiceImpl() {
		
	}
	
	@Override
	public Response add(ArrayList<AgentType> types) {
		
		try{
			bean.addAllTypes(types);
			return Response.status(Response.Status.OK).build();
		}catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@Override
	public Response delete(ArrayList<AgentType> types) {
		
		try{
			bean.removeAllTypes(types);
			return Response.status(Response.Status.OK).build();
		}catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	
	@Override
	public ArrayList<AgentType> getAgentTypes() {
		return bean.getAllTypes();
	}

	@Override
	public ArrayList<Agent> getRunningAgents() {
		return bean.getRunningAgents();
	}
	

	@Override
	public Response addRunningAgents(ArrayList<Agent> agents) {
		
		try{
			bean.addRunningAgents(agents);
			return Response.status(Response.Status.OK).build();
		}catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}
	
	@Override
	public Response removeRunningAgents(ArrayList<Agent> agent) {
		try{
			bean.removeRunningAgents(agent);
			return Response.status(Response.Status.OK).build();
		}catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	@Override
	public Response startAgent(String stype, String alias) {
		try{
			JSONObject object = new JSONObject(stype);
			String name = object.getString("name");
			String module = object.getString("module");
			AgentType type = new AgentType(name, module);
			Agent agent = new Agent(new AID(alias, ClusterBean.getLocal(), type));
			
			bean.addRunningAgent(agent);
			
			ArrayList<AgentCenter> centers = clusterBean.getAllClusters();
			for(int i=0; i<centers.size(); i++){
				
				if(!centers.get(i).getAddress().equals(ClusterBean.getLocal().getAddress())){
					AgentService service = agentEndPoint(centers.get(i).getAddress());
					ArrayList<Agent> ag = new ArrayList<>();
					ag.add(agent);
					service.addRunningAgents(ag);
				}
			}
			return Response.status(Response.Status.OK).build();
		}catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@Override
	public Response stopAgent(String stype) {
		try{
			JSONObject object = new JSONObject(stype);
			String name = object.getString("name");
			String sHost = object.getString("host");
			String sType = object.getString("type");
			JSONObject jsonHost = new JSONObject(sHost);
			String hAddress = jsonHost.getString("address");
			String hAlias = jsonHost.getString("alias");
			JSONObject jsonType = new JSONObject(sType);
			String tName = jsonType.getString("name");
			String tModule = jsonType.getString("module");
			AgentType type = new AgentType(tName, tModule);
			AgentCenter center = new AgentCenter(hAddress, hAlias);
			AID aid = new AID(name, center, type);
			
			Agent agent = bean.getRunningAgent(aid);
			bean.removeRunningAgent(agent);
			ArrayList<AgentCenter> centers = clusterBean.getAllClusters();
			for(int i=0; i<centers.size(); i++){
				
				if(!centers.get(i).getAddress().equals(ClusterBean.getLocal().getAddress())){
					AgentService service = agentEndPoint(centers.get(i).getAddress());
					ArrayList<Agent> ag = new ArrayList<>();
					ag.add(agent);
					service.removeRunningAgents(ag);
				}
			}
			return Response.status(Response.Status.OK).build();
		}catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}	

	public AgentService agentEndPoint(String host){
		Client client = ClientBuilder.newClient();
		String url = "http://"+host+"/AT2/rest";
        WebTarget target = client.target(url);
        ResteasyWebTarget rtarget = (ResteasyWebTarget)target;
        AgentService agentService = rtarget.proxy(AgentService.class);
        return agentService;
	}

	

	

}
