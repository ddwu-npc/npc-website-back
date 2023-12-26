package com.npcweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npcweb.dao.jpa.JpaProjectDAO;
import com.npcweb.domain.Project;
import com.npcweb.domain.User;
import com.npcweb.repository.ProjectRepository;
import com.npcweb.repository.UserRepository;

@Service
public class ProjectService {
	@Autowired
	ProjectRepository projectRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	JpaProjectDAO jpaProjectDao;
	
	public void insert(Project p) {
		// 리더(프로젝트 글쓴이)를 프로젝트 인원에 추가
		User u = userRepo.findById(p.getLeader()).get();
		p.getUser().add(u);
		// 리더 유저에 프로젝트 추가
		u.getProjects().add(p);
		// 저장
		projectRepo.save(p);
		userRepo.save(u);
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
		return projectRepo.findById(project_id).get();
	}
	
	public List<Project> getAllProject() {
		return (List<Project>) projectRepo.findAll();
	}
	
	// 프로젝트 가입
	public void signUpProject(long project_id, long user_no) {
		// 프로젝트에 유저 추가
		User u = userRepo.findById(user_no).get();
		Project p = projectRepo.findById(project_id).get();
		p.getUser().add(u);
		// 유저에 프로젝트 추가
		u.getProjects().add(p);
		// 저장
		userRepo.save(u);
		projectRepo.save(p);
	}
}
