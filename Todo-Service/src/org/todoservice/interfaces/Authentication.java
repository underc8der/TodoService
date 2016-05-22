package org.todoservice.interfaces;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.pinterface.beans.UserSession;
import org.todoservice.exception.ErrorHandlerException;
import org.todoservice.security.Authorization;
import org.todoservice.utilities.JsonResponse;
import org.todoservice.utilities.ResponseUtils;

@Path("/auth")
public class Authentication {

	private Authorization auth = Authorization.instance;
	private ResponseUtils respHandler;
	
	@Path("login/{user}/{password}")
	@GET
	@Produces("Application/json")
	public Response login(@PathParam("user") String username,
			@PathParam("password") String password) throws ErrorHandlerException {
		// TODO implement full login
		UserSession session = auth.validate(username, password);
		respHandler = new JsonResponse();
		return respHandler.responseHandler(session);
	}
	
	@Path("logout/{token}")
	@GET
	@Produces("Application/json")
	public Response logout(@PathParam("token") String token) throws ErrorHandlerException {
		// TODO Auto-generated method stub
		respHandler = new JsonResponse();
		UserSession session = auth.logout(token);
		return respHandler.responseHandler(session.getUsername());
	}
}
