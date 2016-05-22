package org.todoservice.services;

import java.util.List;

import org.pinterface.beans.Task;

public interface TaskHandler {
	
	List<Task> getTasks(String usernames);
	Task getTaskById(Integer id, String username);
	List<Task> getTasksByFilter(String filter, String username);
	Boolean removeTask(Integer id, String username);
	Task completeTask(Integer id, String username);
	Task newTask(Task bean);
}
