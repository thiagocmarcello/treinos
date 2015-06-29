package br.com.novotreino.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/servico")
public class Servico {

	@GET
	@Path("index")
	@Produces("text/html")
	public String index() {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<p> ########################################## </p>");
		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}
}
