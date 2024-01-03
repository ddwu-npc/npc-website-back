package com.npcweb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.npcweb.domain.response.PostResponse;
import com.npcweb.domain.response.ProjectInfoResponse;
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
	
	// 팀원 추가 필요
	
	@RequestMapping
	public List<ProjectResponse> getProjectList(@PageableDefault(page = 1) Pageable pageable) {
		int adjustedPage = pageable.getPageNumber() - 1;
	    PageRequest pageRequest = PageRequest.of(adjustedPage, pageable.getPageSize(), pageable.getSort());
	    
	    Page<ProjectResponse> projectPages = projectService.paging(pageRequest);

	    int blockLimit = 5;
	    int startPage = (adjustedPage / blockLimit) * blockLimit + 1;
	    int endPage = Math.min(startPage + blockLimit - 1, projectPages.getTotalPages());

	    List<ProjectResponse> projectList = projectPages.getContent();
	    return projectList;
	}
	
	@GetMapping("/{project_id}")
	public ProjectReq getProjectInfo(@PathVariable long project_id) {
		ProjectReq req = new ProjectReq();
		
		// projectRes로 전달
		Project project = projectService.getProject(project_id);
		ProjectInfoResponse projectRes = new ProjectInfoResponse(project);
		long leader = Long.parseLong(projectRes.getLeader());
		projectRes.setNickname(userService.getNickname(leader));
		req.setProjectRes(projectRes);
		
		// userList
		Set<User> userList = project.getUser();
		HashMap<String, String> userListRes = new HashMap<String, String>();
		
		for (User user : userList) {
			userListRes.put(user.getNickname(), user.getDept().getDname());
		}
		req.setUserList(userListRes);
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
		ProjectInfoResponse projectRes;
		HashMap<String, String> userList = new HashMap<String, String>();
		
		public ProjectReq() {
		}

		public ProjectInfoResponse getProjectRes() {
			return projectRes;
		}

		public HashMap<String, String> getUserList() {
			return userList;
		}
		
		public void setProjectRes(ProjectInfoResponse projectRes) {
			this.projectRes = projectRes;
		}
		
		public void setUserList(HashMap<String, String> userList) {
			this.userList = userList;
		}

	}
}
