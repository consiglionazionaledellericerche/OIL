package it.cnr.helpdesk.rest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang.StringUtils;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;

import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.LdapManagemet.javabeans.Ldap;
import it.cnr.helpdesk.UserManagement.exceptions.LoginFailureException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;

/**
 * This interceptor verify the access permissions for a user based on username
 * and passowrd provided in request
 * */

/**
 * Questa classe verifica i permessi di accesso in base al nome utente e
 * password
 * 
 * @author Aurelio D'Amico
 * @version 1.0 [Feb 6, 2015]
 */
@Provider
public class RestSecurity implements ContainerRequestFilter, RestRoles, RestNames {
	
	@Override
	public void filter(ContainerRequestContext requestContext) {
		//System.out.println(requestContext.getUriInfo().getAbsolutePath());
	
		ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext
				.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
		Method method = methodInvoker.getMethod();
		
		// recupera il nome dell'instanza e controlla se esiste
		String instance = requestContext.getUriInfo().getPathParameters().getFirst(HD);
		if (instance==null && method.isAnnotationPresent(PermitAll.class)) return;
		if (instanceNotExists(instance)) {
			requestContext.abortWith(getNotFound(instance));
			return;			
		}

		// Access allowed for all
		if (method.isAnnotationPresent(PermitAll.class)) return;

		// Access denied for all
		if (method.isAnnotationPresent(DenyAll.class)) {
			requestContext.abortWith(getAccessForbidden());
			return;
		}

		String authorization = null; 
		try {
			authorization = RestHelper.getInstance().getAuthorization(requestContext.getHeaderString(AUTHORIZATION_PROPERTY));
			if (authorization==null) {
				requestContext.abortWith(getAuthenticationRequired());
				return;
			}
		} catch (Exception e) {
			requestContext.abortWith(getServerError(e));
			return;
		}

		// Split username and password tokens
		StringTokenizer tokenizer = new StringTokenizer(authorization, ":");
		String username = tokenizer.nextToken();
		String password = tokenizer.nextToken();

		// Verifying Username and password
//		System.out.println(username);
//		System.out.println(password);
		
		List<UserValueObject> users = null; 
		try {
			users = getHelpdeskUsers(username, password, instance);			
			if (users==null) {
				requestContext.abortWith(getAccessDenied());
				return;
			}
		} catch (Exception e) {
			requestContext.abortWith(getServerError(e));
			return;
		}

		// Verify users roles
		if (method.isAnnotationPresent(RolesAllowed.class)) {
			// Is user allowed to use the method?
			try {
				boolean isNotAllowed = true;
				for (UserValueObject x : users) {
					if (isUserAllowed(instance, x.getProfile(), method.getAnnotation(RolesAllowed.class))) {
						isNotAllowed = false; break;
					}					
				}
				if (isNotAllowed) {
					requestContext.abortWith(getAccessDenied());
					return;
				}
			} catch (Exception e) {
				requestContext.abortWith(getServerError(e));
				return;
			}
		}
	}
	private List<UserValueObject> getHelpdeskUsers(String username, String password, String instance) throws Exception {
		List<UserValueObject> list = new ArrayList<UserValueObject>();
		String value = Settings.getProperty("it.oil.ldap", instance);
		if (StringUtils.isNotBlank(value) && value.startsWith("enabled")) {
			Ldap ldap = new Ldap();
			if (!ldap.validUserid(username)) return null;
			String cnrapp3 = null;
			try {
				cnrapp3 = ldap.login(username, password, instance);
			} catch (LoginFailureException e) {
				return null;
			}
			if (cnrapp3 == null) return null;
			final StringTokenizer tokA = new StringTokenizer(cnrapp3, ";");
			while(tokA.hasMoreTokens()) {
				final StringTokenizer tokB = new StringTokenizer(tokA.nextToken(), ":");
				String helpdesk = tokB.nextToken();
				if (helpdesk.equals(instance)) {
					String name = tokB.nextToken();
					UserValueObject user = RestHelper.getInstance().getUtente(instance, name);
					if (user!=null) list.add(user);
				}
			}
		} else {
			User user = new User();
			user.setLogin(username);
			user.setPlainPassword(password);
			if (user.isRegistered(instance))
				list.add(user.getDetail(instance));
		}
		return list.isEmpty() ? null : list;
	}
	private boolean instanceNotExists(String instance) {
		final StringTokenizer schemi = new StringTokenizer(System.getProperty(INSTANCE_NAMES), ";");
		while(schemi.hasMoreTokens()) {
			String token = schemi.nextToken();
			if (token.equals(instance)) return false; 
		}
		return true;
	}
	private boolean isUserAllowed(String instance, int roleId, RolesAllowed rolesAnnotation) throws Exception {
		Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
		return rolesSet.contains(RestHelper.getInstance().getRoleName(instance, roleId));
	}
	private ServerResponse getAuthenticationRequired() {
		int status = HttpServletResponse.SC_UNAUTHORIZED;
		Headers<Object> headers = new Headers<Object>();
		headers.add(AUTENTHICATE, AUTHENTICATION_SCHEME + AUTHENTICATION_REALM);
		return new ServerResponse(status + " - Authentication required for this resource", status, headers);
	}
	private ServerResponse getAccessDenied() {
		int status = HttpServletResponse.SC_UNAUTHORIZED;
		return new ServerResponse(status + " - Access denied for this resource", status, new Headers<Object>());
	}
	private ServerResponse getAccessForbidden() {
		int status = HttpServletResponse.SC_FORBIDDEN;
		return new ServerResponse(status + " - Access forbidden for this resource", status, new Headers<Object>());
	}
	private ServerResponse getServerError(Exception e) {
		int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		String s = e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();		
		return new ServerResponse(status + " - " + s, status, new Headers<Object>());
	}
	private Response getNotFound(String instance) {
		String x = instance == null ? "" : instance;
		int status = HttpServletResponse.SC_NOT_FOUND;
		return new ServerResponse(status + " - " + x + " not found", status, new Headers<Object>());
	}

}
