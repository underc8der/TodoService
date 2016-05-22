package org.todoservice.interfaces;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.pinterface.beans.Task;
import org.pinterface.beans.UserSession;
import org.todoservice.exception.BussinesException;
import org.todoservice.security.Authorization;
import org.todoservice.services.CTaskHandler;
import org.todoservice.services.TaskHandler;
import org.todoservice.utilities.ResponseUtils;

@Path("/task")
public class TaskResource {

	private Authorization auth = Authorization.instance;
	ResponseUtils respHandler;
	
	@Path("/readall?apiKey={key}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Task> getTasks(@PathParam("key") String key) {
		TaskHandler handler = new CTaskHandler();
		UserSession session = auth.isLogged(key);
		return handler.getTasks(session.getUsername());
	}
	
	@Path("byid/{id}/action?apiKey={key}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Task getTaskById(@PathParam("id") Integer id,
			@PathParam("key") String key) {
		TaskHandler handler = new CTaskHandler();
		UserSession session = auth.isLogged(key);
		return handler.getTaskById(id, session.getUsername());
	}
	
	@Path("byfilter/{filter}/action?apiKey={key}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Task> getTasksByFilter(@PathParam("filter") String filter,
			@PathParam("key") String key) {
		TaskHandler handler = new CTaskHandler();
		UserSession session = auth.isLogged(key);
		return handler.getTasksByFilter(filter, session.getUsername());
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void createTask(@PathParam("summary") String summary,
			@PathParam("description") String description,
			@PathParam("token") String token) throws BussinesException{
		UserSession session = auth.isLogged(token);
		if(session == null)
			throw new BussinesException();
		TaskHandler handler = new CTaskHandler();
		Task task = new Task();
		task.setSummary(summary);
		if(description != null){
			task.setDescription(description);
		}
		task.setUsername(session.getUsername());
		handler.newTask(task);
	}
}
