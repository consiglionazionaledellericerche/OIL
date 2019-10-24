package it.cnr.helpdesk.rest;

import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Servizio RESTful per la gestione degli utenti
 * @author Aurelio D'Amico
 * @version 1.0 [16-Feb-2015]
 *
 */
@Path(RestNames.PUSER)
@Produces(MediaType.APPLICATION_JSON)
public class UserRestService extends AbstractRestService {
	
	@Context
    private HttpServletRequest request;
	
	@GET
    @Path(PHD)
    @RolesAllowed(ADMIN)	
    public Response doGet(@PathParam(HD) String hd) {
    	List<UserValueObject> list = null;
    	try {
    		int id = getHelper().getRoleId(hd, RestRoles.EXPERT);
    		if (id>=0)
    			list = getHelper().getUtenti(hd,id);
    	} catch (Exception e) {
    		return getInternalServerError(e);
    	}
    	return list==null ? getNotFoundError(null) : getOk(list);
    }
	@GET
    @Path(PHDID)
    @RolesAllowed(ADMIN)	
    public Response doGet(@PathParam(HD) String hd, @PathParam(ID) String id) {
    	UserValueObject user = null;
    	try {
			user = getHelper().getUtente(hd,id);
    	} catch (Exception e) {
    		return getInternalServerError(e);
    	}
    	return user==null ? getNotFoundError(null) : getOk(user);
    }
    @POST
    @Path(PHD)
    @RolesAllowed(ADMIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doPost(@PathParam(HD) String hd, UserValueObject x) {
        return saveItem(hd, x);
    }
	@PUT
	@Path(PHD)
    @RolesAllowed(ADMIN)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response doPut(@PathParam(HD) String hd, UserValueObject x) {
        return saveItem(hd, x);
    }
	@DELETE
	@Path(PHDID)
    @RolesAllowed(ADMIN)
    public Response doDelete(@PathParam(HD) String hd, @PathParam(ID) String uid) {
		int ok = 0;
        try {
			ok = getHelper().deleteUtente(hd, uid);
		} catch (Exception e) {
    		return getInternalServerError(e);
    	}
        return ok==0 ? getOk() : (ok==1 ? getConflict("utente con segnalazioni attive") : getNotFoundError(uid));
    }

	// private
    private Response saveItem(String hd, UserValueObject x) {
    	if (x==null) return getBadRequestError();
    	boolean isNew = false;
    	try {
			isNew = getHelper().saveUtente(hd, x);
		} catch (Exception e) {
    		return getInternalServerError(e);
		}
    	return isNew ? getCreated() : getOk();
	}	
}