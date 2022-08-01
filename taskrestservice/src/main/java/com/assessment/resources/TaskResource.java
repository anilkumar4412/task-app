package com.assessment.resources;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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

import com.assessment.command.Task;
import com.assessment.dao.TaskDAO;
import com.assessment.entity.TaskEntity;
import com.codahale.metrics.annotation.Timed;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.util.Strings;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/task")
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {
	
	private TaskDAO taskDAO;
	
	public TaskResource(TaskDAO taskDAO) {
		super();
		this.taskDAO = taskDAO;
	}
	
	@POST
	@Timed
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	public Task create(@Valid Task task) {
		TaskEntity taskEntity =  TaskEntity.builder().description(task.getDescription()).build();
		try {
			if(!Strings.isNullOrEmpty(task.getTaskDate()))
				taskEntity.setDate(LocalDate.parse(task.getTaskDate(), DateTimeFormatter.ISO_DATE));
		}catch (DateTimeParseException e) {
			log.error("failed to parse task date..");
		}
	    Long taskId = taskDAO.create(taskEntity);
	    task.setId(taskId);
	    return task;
	}
	
	@PUT
	@Path("/{id}")
	@Timed
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	public Task update(@Valid Task task) {
		TaskEntity taskEntity = taskDAO.findById(task.getId());
		taskEntity.setDescription(task.getDescription());
		try {
			if(!Strings.isNullOrEmpty(task.getTaskDate()))
				taskEntity.setDate(LocalDate.parse(task.getTaskDate(), DateTimeFormatter.ISO_DATE));
		}catch (DateTimeParseException e) {
			log.error("failed to parse task date..");
		}
	    Long taskId = taskDAO.create(taskEntity);
	    task.setId(taskId);
	    return task;
	}
	
	@GET
	@Path("/{id}")
	@Timed
	@UnitOfWork
	public Task findTask(@PathParam("id") Long id) {
	    TaskEntity taskEntity = taskDAO.findById(id);
	    Task task =  Task.builder().id(taskEntity.getId()).description(taskEntity.getDescription()).build();
	    if(taskEntity.getDate()!=null)
				task.setTaskDate(taskEntity.getDate().toString());
	    return task;
	}
	
	@GET
	@Timed
	@UnitOfWork
	public List<Task> getAllTasks() {
	    List<TaskEntity> taskEntityList = taskDAO.findAll();
	    List<Task> taskList = new ArrayList<Task>();
	    taskEntityList.forEach(t -> {
	    	Task task = Task.builder().id(t.getId()).description(t.getDescription()).build();
	    	if(t.getDate()!=null)
				task.setTaskDate(t.getDate().toString());
	    	taskList.add(task);
	    });
	    return taskList;
	}
	
	@DELETE
	@Path("/{id}")
	@Timed
	@UnitOfWork
	public void delete(@PathParam("id") Long id) {
		 taskDAO.deleteById(id);
	}
}
