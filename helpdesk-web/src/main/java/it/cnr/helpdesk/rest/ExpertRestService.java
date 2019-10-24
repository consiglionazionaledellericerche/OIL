package it.cnr.helpdesk.rest;

import it.cnr.helpdesk.CategoryManagement.valueobjects.CategoryValueObject;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Servizio RESTful per la gestione degli esperti
 * (associazione utenti a categorie)
 * @author Aurelio D'Amico
 * @version 1.0 [19-Feb-2015]
 *
 */
@Path(RestNames.PUCAT)
@Produces(MediaType.APPLICATION_JSON)
public class ExpertRestService extends AbstractRestService {
	
	@GET
    @Path(PHDID)
    @RolesAllowed({ADMIN,EXPERT,VALIDATOR})	
    public Response doGet(@PathParam(HD) String hd, @PathParam(ID) String id) {
		try {
			int categoryId = Integer.parseInt(id);
			return getUsersByCategory(hd, categoryId);
		} catch (NumberFormatException e) {
			return getCategoriesByUser(hd, id);
		}
    }
	@POST
	@Path(PHDIDUID)
    @RolesAllowed(ADMIN)
    public Response doPost(@PathParam(HD) String hd, @PathParam(ID) Integer id, @PathParam(UID) String uid) {
        return saveItem(hd, id, uid);
    }
	@PUT
	@Path(PHDIDUID)
    @RolesAllowed(ADMIN)
    public Response doPut(@PathParam(HD) String hd, @PathParam(ID) Integer id, @PathParam(UID) String uid) {
        return saveItem(hd, id, uid);
    }
	@DELETE
	@Path(PHDIDUID)
    @RolesAllowed(ADMIN)
    public Response doDelete(@PathParam(HD) String hd, @PathParam(ID) Integer id, @PathParam(UID) String uid) {
		boolean ok = false;
        try {
			ok = getHelper().deleteExpert(hd, id, uid);
		} catch (Exception e) {
    		return getInternalServerError(e);
    	}
        return ok ?  getOk() : getNotFoundError(null);
    }
	
	// private
    private Response saveItem(String hd, Integer id, String uid) {
		boolean ok = false;
    	try {
    		ok = getHelper().saveExpert(hd, id, uid); 
		} catch (Exception e) {
    		return getInternalServerError(e);
		}
    	return ok ? getOk() : getNotFoundError(null);
	}    
    private Response getUsersByCategory(String hd, Integer id) {
    	List<UserValueObject> list = null;
    	try {
			list = getHelper().getUsersByCategory(hd, id);
    	} catch (Exception e) {
    		return getInternalServerError(e);
    	}
    	return list==null ? getNotFoundError(null) : getOk(list);
    }
    private Response getCategoriesByUser(String hd, String uid) {
    	List<CategoryValueObject> list = null;
    	try {
			list = getHelper().getCategoriesByUser(hd, uid);
    	} catch (Exception e) {
    		return getInternalServerError(e);
    	}
    	return list==null ? getNotFoundError(null) : getOk(list);
    }
}