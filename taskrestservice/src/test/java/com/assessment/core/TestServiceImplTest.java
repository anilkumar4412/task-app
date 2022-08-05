package com.assessment.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.assessment.api.Task;
import com.assessment.core.service.impl.TaskServiceImpl;
import com.assessment.db.TaskDAO;
import com.assessment.db.entity.TaskEntity;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import javassist.expr.Instanceof;

@ExtendWith(DropwizardExtensionsSupport.class)
public class TestServiceImplTest {
	
	private static final TaskDAO DAO = mock(TaskDAO.class);
	private static final TaskServiceImpl  service = new TaskServiceImpl(DAO);
	private Task task;
	private TaskEntity taskEntity;
	
	@BeforeEach
    void setup() {
        task = new Task();
        task.setId(1L);
        taskEntity = new TaskEntity();
        taskEntity.setId(1L);
    }

    @AfterEach
    void tearDown() {
        reset(DAO);
    }
    
    @Test
    void getTaskSuccess() {
        when(DAO.findById(1L)).thenReturn(taskEntity);

        Task found = service.getTask(1L);

        assertThat(found.getId()).isEqualTo(task.getId());
        verify(DAO).findById(1L);
    }
    
    @Test
    void createTask() {
    	TaskEntity testEntity = TaskEntity.builder().description("test").build();
        when(DAO.create(testEntity)).thenReturn(taskEntity.getId());
        
        Task found = service.createTask(Task.builder().description("test").build());
        
        assertThat(found.getId()).isEqualTo(taskEntity.getId());
        verify(DAO).create(testEntity);
    }
    
    @Test
    void updateTask() {
    	TaskEntity testTaskEntity = TaskEntity.builder().id(1L).description("test").build();
        when(DAO.findById(testTaskEntity.getId())).thenReturn(testTaskEntity);
        when(DAO.create(testTaskEntity)).thenReturn(testTaskEntity.getId());

        Task found = service.updateTask(Task.builder().id(1L).description("test").build());

        assertThat(found.getId()).isEqualTo(testTaskEntity.getId());
        verify(DAO).findById(testTaskEntity.getId());
        verify(DAO).create(testTaskEntity);
    }

}
