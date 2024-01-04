package com.npcweb.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
    @GetMapping
    public ResponseEntity<Map<String, Object>> getProjectList(@PageableDefault(page = 1) Pageable pageable) {
        int adjustedPage = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(adjustedPage, pageable.getPageSize(), pageable.getSort());

        Page<ProjectResponse> projectPages = projectService.paging(pageRequest);
        List<Integer> pageInfo = new ArrayList<>();

        int blockLimit = 5;
        int startPage = (adjustedPage / blockLimit) * blockLimit + 1;
        int endPage = Math.min(startPage + blockLimit - 1, projectPages.getTotalPages());
        pageInfo.add(adjustedPage + 1);
        pageInfo.add(endPage);
        
        List<ProjectResponse> projectList = projectPages.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("projects", projectList);
        response.put("pageInfo", pageInfo);

        return ResponseEntity.ok().body(response);
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
	
	@GetMapping("/create/{userno}")
	public void createProject(@PathVariable String userno) {
	    User user = userService.getUserByNickname(userno);
	    Date currentDate = new Date();
	    
	    Project project = null;
		
		project.setPname("");
		project.setContent("");
		project.setEndDate(currentDate);
		project.setStartDate(currentDate);
		project.setTname("");
		project.setProcess("0");
		project.setType("");
		project.setLeader(user.getUserNo());
		
		

	}
	
	@DeleteMapping("/{project_id}")
	public void deleteProject(@PathVariable long project_id) {
		projectService.delete(project_id);
	}
	
	@PutMapping("/{project_id}")
	public ResponseEntity<?> updateProject(@PathVariable("project_id") Long projectId, @RequestBody ProjectReqRes projectRes) throws ParseException {

	    Project project = projectService.getProject(projectId);
	    User user = userService.getUserByNickname(projectRes.getLeader());
	    
	    project.setPname(projectRes.getPname());
	    project.setProcess(projectRes.getProcess());
	    
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date startDate = dateFormat.parse(projectRes.getStartDate());
	    Date endDate = dateFormat.parse(projectRes.getEndDate());
	    
	    project.setStartDate(startDate);
	    project.setEndDate(endDate);
	    
	    
	    projectService.update(project);
	    
	    Project updatedProject = projectService.getProject(projectId);
	    return ResponseEntity.ok(updatedProject);
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
	
	static class ProjectReqRes {
		long pid;
		String leader;
		String pname, tname, content, type, process;
		String startDate, endDate;
		
		public ProjectReqRes() {
		}
		
		public long getPid() {
			return pid;
		}

		public void setPid(long pid) {
			this.pid = pid;
		}

		public String getLeader() {
			return leader;
		}

		public void setLeader(String leader) {
			this.leader = leader;
		}

		public String getPname() {
			return pname;
		}

		public void setPname(String pname) {
			this.pname = pname;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getTname() {
			return tname;
		}

		public String getStartDate() {
			return startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setTname(String tname) {
			this.tname = tname;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public String getProcess() {
			return process;
		}

		public void setProcess(String process) {
			this.process = process;
		}
	}
}
