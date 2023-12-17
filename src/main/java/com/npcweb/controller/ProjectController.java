package com.npcweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.domain.Project;
import com.npcweb.domain.User;
import com.npcweb.service.ProjectService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/project")
public class ProjectController {
	@Autowired
	ProjectService projectService;
	
	@RequestMapping
	public List<Project> getProjectList() {
		List<Project> projectList = projectService.getAllProject();
		return projectList;
	}
	
	@GetMapping("/{project_id}")
	public ProjectReq getProjectInfo(@PathVariable long project_id) {
		ProjectReq req = new ProjectReq();
		req.setP(projectService.getProject(project_id));
		
		return req;
	}
	
	@DeleteMapping("/{project_id}")
	public void deleteProject(@PathVariable long project_id) {
		projectService.delete(project_id);
	}
	
	@PostMapping
    public void insertProject(@RequestBody Project project) {
		projectService.insert(project);
	}
	
	class ProjectReq {
		Project project;
		List<String> userList = new ArrayList<String>();
		
		public ProjectReq() {
			userList.add("1118");
		}
		
		public Project getProject() {
			return project;
		}
		public void setP(Project p) {
			this.project = p;
		}

		public List<String> getUserList() {
			return userList;
		}

		public void setUserList(List<String> userList) {
			this.userList = userList;
		}
		
		
	}
}
