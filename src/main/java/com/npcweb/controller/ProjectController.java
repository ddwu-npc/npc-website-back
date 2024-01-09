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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.domain.Project;
import com.npcweb.domain.User;
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

        //int startPage = 1;
        int endPage = projectPages.getTotalPages();
        pageInfo.add(adjustedPage);
        pageInfo.add(endPage);
        
        List<ProjectResponse> projectList = projectPages.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("projects", projectList);
        response.put("pageInfo", pageInfo);

        return ResponseEntity.ok().body(response);
    }
	
    // 프로젝트 검색
  	@PostMapping("/search")
  	public ResponseEntity<Map<String, Object>> pageBySearch(@RequestBody Map<String, String> requestBody, @PageableDefault(page = 1) Pageable pageable) {
		String _process = requestBody.get("process");	
  		String _type = requestBody.get("type");
  		String pname = requestBody.get("text");
  		int process = (_process != null) ? Integer.parseInt(_process) : 0;
  		int type = (_type != null) ? Integer.parseInt(_type) : 0;
  		
  		// 검색 결과 리스트
  		List<Project> projectListBySearch = projectService.searchProjects(process, type, pname);
  		
  	    int adjustedPage = pageable.getPageNumber() - 1;
  	    PageRequest pageRequest = PageRequest.of(adjustedPage, pageable.getPageSize(), pageable.getSort());
  	    
  	    // 페이징
  	    Page<ProjectResponse> projectPages = projectService.pagingBySearch(pageRequest, projectListBySearch);
  	    int endPage = projectListBySearch.size() / 11 + 1; // service 참고

        Map<String, Object> response = new HashMap<>();

  	    List<ProjectResponse> projectList = projectPages.getContent();
  	    List<Integer> pageInfo = new ArrayList<Integer>();
  	    pageInfo.add(adjustedPage); pageInfo.add(endPage);
  	   
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
	
	// 프로젝트 팀원 추가
	@PostMapping("/add/{pid}/{nickname}")
	public void insertProjectUser(@PathVariable String nickname, @PathVariable long pid) {
		User user = userService.getUserByNickname(nickname);
		projectService.signUpProject(pid, user.getUserNo());
	}
	
	// 프로젝트 팀원 삭제
	@PostMapping("/remove/{pid}/{nickname}")
	public void removeProjectUser(@PathVariable String nickname, @PathVariable long pid) {
		User user = userService.getUserByNickname(nickname);
		projectService.leaveProject(pid, user.getUserNo());
		
	}
	
	// 프로젝트 리더(팀장) 변경
	@PostMapping("/update/{pid}/{nickname}")
	public void updateProjectLeader(@PathVariable String nickname, @PathVariable long pid) {
		User user = userService.getUserByNickname(nickname);
		projectService.changeLeader(pid, user.getUserNo());
		
	}
	
	@PutMapping("/create")
	public ResponseEntity<?> createProject(@RequestBody ProjectReq projectRes) throws ParseException {

		User user = userService.getUserByNickname(projectRes.projectRes.getLeader());
		
	    Project project = new Project();

	    // 리더 설정
	    project.setLeader(user.getUserNo());
	    // 날짜 변환
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date startDate = dateFormat.parse(projectRes.projectRes.getStartDate());
	    Date endDate = dateFormat.parse(projectRes.projectRes.getEndDate());
	    
	    project.setStartDate(startDate);
	    project.setEndDate(endDate);
	    project.setContent(projectRes.projectRes.getContent());
	    project.setPname(projectRes.projectRes.getPname());
	    project.setTname(projectRes.projectRes.getTname()); 
	    project.setProcess(projectRes.projectRes.getProcess());
	    project.setType(projectRes.projectRes.getType());
	    	    
	    long newPid = projectService.insert(project);

	    for (Map.Entry<String, String> entry : projectRes.userList.entrySet()) {
	        String key = entry.getKey();
	        
	        if (!key.equals(projectRes.projectRes.getLeader())){
	        	User u = userService.getUserByNickname(key);
	        	projectService.signUpProject(newPid, u.getUserNo());
	        }
	    }
	    
	    return ResponseEntity.ok(true);
	}
	
	@DeleteMapping("/{project_id}")
	public void deleteProject(@PathVariable long project_id) {
		projectService.delete(project_id);
	}
	
	@PutMapping("/{project_id}")
	public ResponseEntity<?> updateProject(@PathVariable("project_id") Long projectId, @RequestBody ProjectReq projectRes) throws ParseException {

	    Project project = projectService.getProject(projectId);
	    // 날짜 변환
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date startDate = dateFormat.parse(projectRes.projectRes.getStartDate());
	    Date endDate = dateFormat.parse(projectRes.projectRes.getEndDate());
	    
	    project.setStartDate(startDate);
	    project.setEndDate(endDate);
	    project.setContent(projectRes.projectRes.getContent());
	    project.setPname(projectRes.projectRes.getPname());
	    project.setTname(projectRes.projectRes.getTname());
	    project.setProcess(projectRes.projectRes.getProcess());
	    project.setType(projectRes.projectRes.getType());
	    
	    projectService.update(project);
	    Project updatedProject = projectService.getProject(projectId);
	    return ResponseEntity.ok(updatedProject);
	}
	
	static class ProjectReq {
		private ProjectInfoResponse projectRes;
		private HashMap<String, String> userList = new HashMap<String, String>();
		
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
