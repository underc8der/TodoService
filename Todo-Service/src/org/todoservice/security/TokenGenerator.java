package org.todoservice.security;

public interface TokenGenerator {

	String encript(String username, String securityCode, String cookie);
	
	String encript(String username, String securityCode);
}
