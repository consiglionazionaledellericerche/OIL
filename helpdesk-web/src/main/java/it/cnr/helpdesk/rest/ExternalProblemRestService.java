package it.cnr.helpdesk.rest;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.cnr.helpdesk.rest.model.ExternalProblem;


/**
 * Servizio RESTful per la gestione dei problemi degli utenti esterni
 * @author Aurelio D'Amico
 * @version 1.0 [15-Apr-2016]
 *
 */
@Path(RestNames.PEST)
@Produces(MediaType.APPLICATION_JSON)
public class ExternalProblemRestService extends AbstractRestService {
	
	@GET
    @Path(PHD)
    public Response doGet(@PathParam(HD) String hd) {
    	return getOk();
    }
	
    @PUT
    @Path(PHD)
    @Consumes(MediaType.APPLICATION_JSON)    
    public Response doPut(@PathParam(HD) String hd, ExternalProblem x) {
        return saveItem(hd, x, true);
    }

    @POST
    @Path(PHD)
    @Consumes(MediaType.APPLICATION_JSON)    
    public Response doPost(@PathParam(HD) String hd, ExternalProblem x) {
        return saveItem(hd, x, false);
    }

    @POST
    @Path(PHDID)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response doPost(@PathParam(HD) String hd, @PathParam(ID) Integer sid, MultipartFormDataInput x) {
        return addAllegato(hd, x, sid);
    }

    private Response saveItem(String hd, ExternalProblem x, boolean isNew) {
    	if (x==null) return getBadRequestError();
		long id = 0; 
    	try {
			id = getHelper().saveExternalProblem(hd, x, isNew);
		} catch (BadRequestException be) {
			return getBadRequestError(be.getMessage());
		} catch (Exception e) {
    		return getInternalServerError(e);
		}
		if (id==0) return getNotFoundError(""+x.getIdSegnalazione());
		return isNew ? getCreated(id) : getOk();
	}

	// private
    private Response addAllegato(String hd, MultipartFormDataInput x, Integer sid) {
    	if (x==null) return getBadRequestError();
    	boolean ok = false;
    	try {
			ok = getHelper().addAllegato(hd, x, sid);
		} catch (Exception e) {
    		return getInternalServerError(e);
		}
		return ok ? getOk() : getNotFoundError(""+sid);
	}    
}