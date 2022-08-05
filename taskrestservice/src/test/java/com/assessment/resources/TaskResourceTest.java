package com.assessment.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.assessment.api.Task;
import com.assessment.core.service.TaskService;
import com.assessment.core.service.impl.TaskServiceImpl;
import com.assessment.db.TaskDAO;
import com.assessment.db.entity.TaskEntity;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;

@ExtendWith(DropwizardExtensionsSupport.class)
public class TaskResourceTest {

    private static final TaskDAO DAO = mock(TaskDAO.class);
    private static final TaskService service = mock(TaskServiceImpl.class);
    private static final ResourceExtension EXT = ResourceExtension.builder()
            .addResource(new TaskResource(service))
            .build();
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
        reset(service);
    }
    

    @Test
    void getTaskSuccess() {
        when(service.getTask(1L)).thenReturn(task);

        Task found = EXT.target("/task/1").request().get(Task.class);

        assertThat(found.getId()).isEqualTo(task.getId());
        verify(service).getTask(1L);
    }

    @Test
    void getTaskNotFound() {
    	when(service.getTask(1L)).thenReturn(task);
        final Response response = EXT.target("/task/2").request().get();
        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(service).getTask(2L);
    }
    
    @Test
    void createTask() {
        when(service.createTask(Task.builder().description("test").build())).thenReturn(task);

        Response found = EXT.target("/task").request().post(Entity.json("{\"description\":\"test\"}"));

        assertThat(found.readEntity(Task.class).getId()).isEqualTo(task.getId());
        verify(service).createTask(Task.builder().description("test").build());
    }
    
    @Test
    void updateTask() {
    	Task testTask = Task.builder().id(1L).description("test").build();
        when(service.updateTask(testTask)).thenReturn(task);

        Response found = EXT.target("/task/1").request().put(Entity.json("{\"id\":1,\"description\":\"test\"}"));

        assertThat(found.readEntity(Task.class).getId()).isEqualTo(task.getId());
        verify(service).updateTask(testTask);
    }

}
