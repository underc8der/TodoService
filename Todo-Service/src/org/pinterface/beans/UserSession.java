package org.pinterface.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author d.gonzalez
 *
 */
@XmlRootElement
public class UserSession {
	private String username;
	private String token;
	private String criptoHandler;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCriptoHandler() {
		return criptoHandler;
	}
	public void setCriptoHandler(String criptoHandler) {
		this.criptoHandler = criptoHandler;
	}
}
