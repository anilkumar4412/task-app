package com.assessment.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
	
	public Task(String description, String taskDate) {
		super();
		this.description = description;
		this.taskDate = taskDate;
	}
	private Long id;
	private String description;
	private String taskDate;
	
	
}
