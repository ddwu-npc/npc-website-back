package com.npcweb.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

import com.npcweb.domain.Project;

public interface ProjectDAO extends CrudRepository<Project, Long>{
	public void insertProject(Project project) throws DataAccessException;
	
	public Project readProject(long project_id) throws DataAccessException;
	
	public void updateProject(Project project) throws DataAccessException;

	public void deleteProject(Project project) throws DataAccessException;

}
