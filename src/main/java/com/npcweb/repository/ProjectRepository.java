package com.npcweb.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.npcweb.domain.Project;

public interface ProjectRepository  extends JpaRepository<Project, Long>{
//	public void insertProject(Project project) throws DataAccessException;
//	
//	public Project readProject(long project_id) throws DataAccessException;
//	
//	public void updateProject(Project project) throws DataAccessException;
//
//	public void deleteProject(Project project) throws DataAccessException;
}
