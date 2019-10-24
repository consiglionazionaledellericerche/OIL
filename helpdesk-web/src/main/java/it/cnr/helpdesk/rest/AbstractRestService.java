package it.cnr.helpdesk.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

/**
 * Metodi comuni
 * @author Aurelio D'Amico
 * @version 1.0 [19-Jan-2015]
 *
 */
public abstract class AbstractRestService implements RestNames, RestRoles {
	protected RestHelper getHelper() {
		return RestHelper.getInstance();
	}
    protected Response getInternalServerError(Exception e) {
		String s = e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
		s = Status.INTERNAL_SERVER_ERROR + " - " + s;
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(s).build();
	}
	protected Response getNotFoundError(String id) {
		if (id==null) return Response.status(Status.NOT_FOUND).build();
		return Response.status(Status.NOT_FOUND).entity("Identificativo assente: " + id).build();
	}
    protected Response getBadRequestError(String s) {
    	return Response.status(Status.BAD_REQUEST).entity(s).build();
	}
    protected Response getBadRequestError() {
    	return Response.status(Status.BAD_REQUEST).build();
	}
    protected Response getOk() {
    	return Response.status(Status.NO_CONTENT).build();	
    }
    protected Response getOk(Object x) {
    	return Response.ok(getJson(x)).header("Cache-Control","max-age=3600, public").build();
    }
    protected Response getCreated() {
    	return Response.status(Status.CREATED).build();    	
    }
    protected Response getCreated(Object x) {
    	return Response.status(Status.CREATED).entity(x).build();
    }
    protected Response getForbidden() {
    	return Response.status(Status.FORBIDDEN).build();
	}
    protected Response getConflict(String cause) {
    	if (cause==null) return Response.status(Status.CONFLICT).build();
    	return Response.status(Status.CONFLICT).entity(cause).build();
	}
	protected Object getJson(Object x) {
		return new Gson().toJson(x);
	}
}
