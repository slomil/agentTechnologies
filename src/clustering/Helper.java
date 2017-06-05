package clustering;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/help")
public class Helper {

	@Path("/get")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getText(){
		return "Milica";
	}
}
