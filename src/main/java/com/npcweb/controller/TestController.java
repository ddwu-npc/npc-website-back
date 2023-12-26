package com.npcweb.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.npcweb.domain.Project;
import com.npcweb.service.ProjectService;

@RestController
@RequestMapping("/test")
public class TestController {

    private final ProjectService projectService;

    @Autowired
    public TestController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/insert")
    public void insertProject(
		    @RequestParam(value = "leader") long leader,
		    @RequestParam(value = "tname") String title,
		    @RequestParam(value = "content") String content,
		    @RequestParam(value = "pname") String pname)	{

    	Project tmp = new Project();
    	tmp.setLeader(leader);
    	tmp.setTname(title);
    	tmp.setContent(content);
    	tmp.setPname(pname);
    	tmp.setType("개인");
    	tmp.setStartDate(new Date());
    	tmp.setEndDate(new Date());

        projectService.insert(tmp);
    }

    @DeleteMapping("/{projectId}")
    public void deleteProject(@PathVariable long projectId) {
        projectService.delete(projectId);
    }

    @PutMapping("/update")
    public void updateProject(@RequestBody Project project) {
        projectService.update(project);
    }

    @GetMapping("/{projectId}")
    public Project getProject(@PathVariable long projectId) {
        return projectService.getProject(projectId);
    }

    @GetMapping("/all")
    public List<Project> getAllProjects() {
        return projectService.getAllProject();
    }

    @PostMapping("/{projectId}/signup/{userId}")
    public void signUpProject(@PathVariable long projectId, @PathVariable long userId) {
        projectService.signUpProject(projectId, userId);
    }
}
