package com.npcweb.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.npcweb.domain.response.ProjectResponse;
import com.npcweb.service.ProjectService;
import com.npcweb.service.UserService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/project")
public class ProjectController {
	@Autowired
	ProjectService projectService;
	@Autowired
	UserService userService;
	
	@RequestMapping
	public List<Project> getProjectList() {
		List<Project> projectList = projectService.getAllProject();
		return projectList;
	}
	
	@GetMapping("/{project_id}")
	public ProjectReq getProjectInfo(@PathVariable long project_id) {
		ProjectReq req = new ProjectReq();
		// projectRes
		Project project = projectService.getProject(project_id);
		ProjectResponse projectRes = new ProjectResponse(project);
		long leader = Long.parseLong(projectRes.getLeader());
		projectRes.setNickname(userService.getNickname(leader));
		req.setProjectRes(projectRes);
		// userList
		// 작성 필요
		
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
		ProjectResponse projectRes;
		HashMap<String, String> userList = new HashMap<String, String>();
		
		public ProjectReq() {
			// 이 부분 DB 연동 필요
			// 프로젝트 가입 시 DB에 반영되야함. 여기서는 부서랑 닉네임만 출력
			userList.put("1118", "개발팀");
		}

		public ProjectResponse getProjectRes() {
			return projectRes;
		}

		public HashMap<String, String> getUserList() {
			return userList;
		}
		
		public void setProjectRes(ProjectResponse projectRes) {
			this.projectRes = projectRes;
		}
		
		public void setUserList(HashMap<String, String> userList) {
			this.userList = userList;
		}

	}
}
