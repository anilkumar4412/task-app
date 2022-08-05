package com.assessment.core.service;

import java.util.List;

import com.assessment.api.Task;

public interface TaskService {
	Task createTask(Task task);
	Task updateTask(Task task);
	Task getTask(long taskId);
	List<Task> getAllTasks();
	void deleteTask(long taskId);
}
