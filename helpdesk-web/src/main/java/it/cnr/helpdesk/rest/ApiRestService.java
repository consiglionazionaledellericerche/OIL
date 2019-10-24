package it.cnr.helpdesk.rest;

import java.io.InputStream;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;


/**
 * Servizio RESTful per la visualizzazione delle API
 * @author Aurelio D'Amico
 * @version 1.0 [27-Feb-2015]
 *
 */
@Path(RestNames.PAPI)
@Produces(MediaType.TEXT_PLAIN)
public class ApiRestService extends AbstractRestService {
	public static final String FILENAME="it/cnr/helpdesk/rest/api/restapi.txt";

	@GET
	@PermitAll
    public String doGet() {
		String text = "";
		try {
			text = getResourceAsString(FILENAME);
		} catch (Exception e) {
			// do nothing
		}
		return text;
    }
	public static String getResourceAsString(String resourceName) throws Exception {
		return IOUtils.toString(getResource(resourceName));
	}
	public static InputStream getResource(String resourceName) throws Exception {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader!=null) {
			return loader.getResourceAsStream(resourceName);
		}
		// attempt to load from the system classpath
		return ClassLoader.getSystemResourceAsStream(resourceName);
	}	
}