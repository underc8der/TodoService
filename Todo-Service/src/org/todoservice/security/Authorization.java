package org.todoservice.security;

import java.util.HashMap;
import java.util.Map;

import org.pinterface.beans.UserSession;

public enum Authorization {
	instance;
	
	private GeneratorFactoryMethod factory = new GeneratorFactory();
	private Map<String, UserSession> sessionMap = new HashMap<>();
	
	public UserSession isLogged(String key){
		return sessionMap.get(key);
	}
	
	public UserSession logout(String token) {
		UserSession result = null;
		if(sessionMap.containsKey(token)){
			result = sessionMap.remove(token);
		}
		return result;
	}
	
	public UserSession validate(String username, String password) {
		UserSession session = new UserSession();
		String scode = isAValidUser(username, password);
		if(scode != null){
			String token = factory.createEncript("MD5", username, scode);
			session.setCriptoHandler("MD5");
			session.setToken(token);
			session.setUsername(username);
			sessionMap.put(token, session);
		}
		return session;
	}
	
	/**
	 * Check if the user exist bringing the security code
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	private String isAValidUser(String username, String password){
		// TODO implement
		return "";
	}
}
