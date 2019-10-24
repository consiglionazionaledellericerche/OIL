package it.cnr.helpdesk.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ping")
public class PingRestService {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response doGet() {
		return Response.status(200).entity("pong").build();	
	}
}
