package com.assessment.core.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.assessment.api.Task;
import com.assessment.core.service.TaskService;
import com.assessment.db.TaskDAO;
import com.assessment.db.entity.TaskEntity;

import io.dropwizard.util.Strings;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskServiceImpl implements TaskService {
	
	private TaskDAO taskDAO;
	
	public TaskServiceImpl(TaskDAO taskDAO) {
		super();
		this.taskDAO = taskDAO;
	}

	@Override
	public Task createTask(Task task) {
		TaskEntity taskEntity =  TaskEntity.builder().description(task.getDescription()).build();
		try {
			if(!Strings.isNullOrEmpty(task.getTaskDate()))
				taskEntity.setDate(LocalDate.parse(task.getTaskDate(), DateTimeFormatter.ISO_DATE));
		}catch (DateTimeParseException e) {
			log.error("failed to parse task date..");
			throw e;
		}
	    Long taskId = taskDAO.create(taskEntity);
	    task.setId(taskId);
	    return task;
	}
	
	
	@Override
	public Task updateTask(Task task) {
		TaskEntity taskEntity = taskDAO.findById(task.getId());
		taskEntity.setDescription(task.getDescription());
		try {
			if(!Strings.isNullOrEmpty(task.getTaskDate()))
				taskEntity.setDate(LocalDate.parse(task.getTaskDate(), DateTimeFormatter.ISO_DATE));
		}catch (DateTimeParseException e) {
			log.error("failed to parse task date..");
			throw e;
		}
	    Long taskId = taskDAO.create(taskEntity);
	    task.setId(taskId);
	    return task;
	}

	@Override
	public Task getTask(long taskId) {
	    TaskEntity taskEntity = taskDAO.findById(taskId);
	    Task task =  Task.builder().id(taskEntity.getId()).description(taskEntity.getDescription()).build();
	    if(taskEntity.getDate()!=null)
				task.setTaskDate(taskEntity.getDate().toString());
	    return task;
	}

	@Override
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

	@Override
	public void deleteTask(long taskId) {
		taskDAO.deleteById(taskId);

	}

}
