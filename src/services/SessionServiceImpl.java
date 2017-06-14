package services;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import beans.ClusterBean;
import beans.SessionBean;
import model.AgentCenter;

@Stateless
public class SessionServiceImpl implements SessionService{

	@EJB
	private SessionBean bean;

	@Override
	public void infromClient(String message) {
		AgentCenter center = ClusterBean.getLocal();
		Session sess = bean.getSession(center.getAddress());
		try {
			sess.getBasicRemote().sendObject(message);
		} catch (IOException | EncodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
