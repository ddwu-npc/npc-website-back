package com.npcweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npcweb.dao.jpa.JpaProjectDAO;
import com.npcweb.domain.Project;
import com.npcweb.repository.ProjectRepository;

@Service
public class ProjectService {
	@Autowired
	ProjectRepository projectRepo;
	@Autowired
	JpaProjectDAO jpaProjectDao;
	
	public void insert(Project p) {
		// 추가
		projectRepo.save(p);
	}
	
	public void delete(long project_id) {
		projectRepo.deleteById(project_id);;
	}
	
	public void update(Project p) {
		// 존재할 시 update, 하지 않으면 insert
		projectRepo.save(p);
	}
	
	public Project getProject(long project_id) {
		// null이면 예러 전달, 아닐 시 객체 전달
		return projectRepo.findById(project_id).orElse(null);
	}
	
	public List<Project> getAllProject() {
		return (List<Project>) projectRepo.findAll();
	}
}
