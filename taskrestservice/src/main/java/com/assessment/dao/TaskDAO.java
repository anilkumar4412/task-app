package com.assessment.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.assessment.entity.TaskEntity;

import io.dropwizard.hibernate.AbstractDAO;

public class TaskDAO extends AbstractDAO<TaskEntity> {

	public TaskDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
    public TaskEntity findById(Long id) {
        return get(id);
    }

    public long create(TaskEntity person) {
        return persist(person).getId();
    }
    
	public List<TaskEntity> findAll() {
    	return (List<TaskEntity>) query("from com.assessment.entity.TaskEntity").getResultList();
    }
	
	public void deleteById(Long id) {
		 currentSession().delete(findById(id));
    }


}
