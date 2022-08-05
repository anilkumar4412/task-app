package com.assessment.resources;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.assessment.api.Task;
import com.assessment.core.service.TaskService;
import com.codahale.metrics.annotation.Timed;

import io.dropwizard.hibernate.UnitOfWork;


@Path("/task")
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {
	private TaskService taskService;
	
	public TaskResource(TaskService taskService) {
		super();
		this.taskService = taskService;
	}
	
	@POST
	@Timed
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(@Valid Task task) {
		return  Response.ok(taskService.createTask(task)).build();
	}
	
	@PUT
	@Path("/{id}")
	@Timed
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@Valid Task task) {
		return  Response.ok(taskService.updateTask(task)).build();
	}
	
	@GET
	@Path("/{id}")
	@Timed
	@UnitOfWork
	public Response findTask(@PathParam("id") Long taskId) {
	    Task task =  taskService.getTask(taskId);
	    if(task==null) {
	    	return Response.status(Response.Status.NOT_FOUND).build();
	    }
	    return Response.ok(task).build();
	}
	
	@GET
	@Timed
	@UnitOfWork
	public List<Task> getAllTasks() {
	    return taskService.getAllTasks();
	}
	
	@DELETE
	@Path("/{id}")
	@Timed
	@UnitOfWork
	public void  delete(@PathParam("id") Long taskId) {
		taskService.deleteTask(taskId);
	}
}
