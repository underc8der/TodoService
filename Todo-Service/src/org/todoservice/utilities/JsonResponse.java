package org.todoservice.utilities;

import javax.ws.rs.core.Response;

import org.todoservice.exception.ErrorHandlerException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonResponse implements ResponseUtils {

	private ObjectWriter ow;
	private int currentStatus = Response.Status.ACCEPTED.getStatusCode();
	
	public JsonResponse() {
		ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	}

	@Override
	public Response responseHandler(String entity) throws ErrorHandlerException {
//		successful(entity);
		if( entity == null )
			entity = "{error: unknow action!}";
		else 
			entity = "{warn: " + entity + "}";
		return Response.status(currentStatus).entity(entity).build();
	}
	
	private String successful(Object entity) throws ErrorHandlerException {
		try {
			return ow.writeValueAsString(entity);
		} catch (JsonProcessingException e) {
			currentStatus = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
			throw new ErrorHandlerException(e);
		}
	}

	@Override
	public Response responseHandler(Object entity) throws ErrorHandlerException {
		String result = successful(entity);
		return Response.status(currentStatus).entity(result).build();
	}
}
