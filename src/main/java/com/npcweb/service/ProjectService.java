package com.npcweb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.npcweb.dao.ProjectDAO;
import com.npcweb.dao.jpa.JpaProjectDAO;
import com.npcweb.domain.Project;

public class ProjectService {
	@Autowired
	ProjectDAO projectDao;
	@Autowired
	JpaProjectDAO jpaProjectDao;
	
	public void insert(Project p) {
		// 추가
		projectDao.insertProject(p);
	}
	
	public void delete(long project_id) {
		projectDao.deleteById(project_id);
	}
	
	public void update(Project p) {
		// 존재할 시 update, 하지 않으면 insert
		projectDao.save(p);
	}
	
	public Project getProject(long project_id) {
		// null이면 예러 전달, 아닐 시 객체 전달
		return projectDao.findById(project_id).orElseThrow();
	}
	
	public List<Project> getAllProject() {
		return (List<Project>) projectDao.findAll();
	}
}
