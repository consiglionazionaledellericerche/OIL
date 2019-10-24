package it.cnr.helpdesk.rest;

import it.cnr.helpdesk.CategoryManagement.valueobjects.CategoryValueObject;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Servizio RESTful per la gestione delle categorie
 * @author Aurelio D'Amico
 * @version 1.0 [15-Jan-2015]
 *
 */
@Path(RestNames.PCATG)
@Produces(MediaType.APPLICATION_JSON)
public class CategoryRestService extends AbstractRestService {
	
	@GET
    @Path(PHD)
    public Response doGet(@PathParam(HD) String hd, @DefaultValue("n") @QueryParam("enabled") String enabled) {
    	List<CategoryValueObject> list = null;
    	try {
			list = getHelper().getCategorie(hd, enabled.toLowerCase());
    	} catch (Exception e) {
    		return getInternalServerError(e);
    	}
    	return list==null ? getNotFoundError(null) : getOk(list.get(0));
    }
	@GET
    @Path(PHDID)
    public Response doGet(@PathParam(HD) String hd, @PathParam(ID) Integer id) {
    	List<CategoryValueObject> list = null;
    	try {
			list = getHelper().getSubCategorie(hd, id);
    	} catch (Exception e) {
    		return getInternalServerError(e);
    	}
    	return list==null ? getNotFoundError(null) : getOk(list);
    }
    @POST
    @Path(PHD)
    @RolesAllowed(ADMIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doPost(@PathParam(HD) String hd, CategoryValueObject x) {
        return saveItem(hd, x);
    }
	@PUT
	@Path(PHD)
    @RolesAllowed(ADMIN)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response doPut(@PathParam(HD) String hd, CategoryValueObject x) {
        return saveItem(hd, x);
    }
	@DELETE
	@Path(PHDID)
    @RolesAllowed(ADMIN)
    public Response doDelete(@PathParam(HD) String hd, @PathParam(ID) Integer id) {
		boolean ok = false;
        try {
			ok = getHelper().deleteCategoria(hd, id);
		} catch (Exception e) {
    		return getInternalServerError(e);
    	}
        return ok ? getOk() : getNotFoundError(id.toString());
    }
	
	// private
    private Response saveItem(String hd, CategoryValueObject x) {
    	if (x==null) return getBadRequestError();
		int id = x.getId();
    	try {
			id = getHelper().saveCategoria(hd, x);
		} catch (Exception e) {
    		return getInternalServerError(e);
		}
		return getOk(id);
	}    
}