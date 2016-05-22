package org.todoservice.exception;

public class ErrorHandlerException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ErrorHandlerException(Throwable e) {
		super(e);
	}

}
