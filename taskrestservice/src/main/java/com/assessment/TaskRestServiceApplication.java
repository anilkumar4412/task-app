package com.assessment;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import com.assessment.dao.TaskDAO;
import com.assessment.db.TaskDBConfiguration;
import com.assessment.entity.TaskEntity;
import com.assessment.resources.TaskResource;
import com.codahale.metrics.health.HealthCheck;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TaskRestServiceApplication extends Application<TaskDBConfiguration> {

    public static void main(final String[] args) throws Exception {
        new TaskRestServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "taskrestservice";
    }
    
    private final HibernateBundle<TaskDBConfiguration> hibernate = new HibernateBundle<TaskDBConfiguration>(TaskEntity.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(TaskDBConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };


    @Override
    public void initialize(final Bootstrap<TaskDBConfiguration> bootstrap) {
    	bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(final TaskDBConfiguration configuration,
                    final Environment environment) {
    	TaskDAO taskDAO = new TaskDAO(hibernate.getSessionFactory());
    	environment.jersey().register(new TaskResource(taskDAO));
    	environment.healthChecks().register("taskhealthcheck", new HealthCheck() {

			@Override
			protected Result check() throws Exception {
				return Result.healthy();
			}
    		
    	});
    	
    	// Enable CORS headers
        final FilterRegistration.Dynamic cors =
            environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

}
