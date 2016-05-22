package org.todoservice.utilities;

import javax.ws.rs.core.Response;

import org.todoservice.exception.ErrorHandlerException;

public interface ResponseUtils {
	Response responseHandler(String entity) throws ErrorHandlerException;
	Response responseHandler(Object entity) throws ErrorHandlerException;
}
