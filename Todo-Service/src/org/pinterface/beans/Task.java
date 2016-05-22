package org.pinterface.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Task {
	private String summary;
	private String description;
	private String username;
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
