package com.npcweb.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.npcweb.domain.Project;
import com.npcweb.domain.User;
import com.npcweb.dto.ProjectResponse;
import com.npcweb.repository.ProjectRepository;
import com.npcweb.repository.UserRepository;
import com.npcweb.specification.ProjectSearchSpecification;

@Service
public class ProjectService {
	@Autowired
	ProjectRepository projectRepo;
	@Autowired
	UserRepository userRepo;
	
	public long insert(Project p) {
		// 리더(프로젝트 글쓴이)를 프로젝트 인원에 추가
		User u = userRepo.findById(p.getLeader()).get();
		p.getUser().add(u);
		// 리더 유저에 프로젝트 추가
		u.getProjects().add(p);
		// 저장
		Project savedProject = projectRepo.save(p);
		userRepo.save(u);
		
		return savedProject.getPid();
	}
	
	public void delete(long project_id) {
		projectRepo.deleteById(project_id);;
	}
	
	public void update(Project p) {
		// 존재할 시 update, 하지 않으면 insert
		changeLeader(p.getPid(), p.getLeader());
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
    @Transactional
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
	
	// 프로젝트 탈퇴
    @Transactional
	public void leaveProject(long projectId, long userno) {
        User u = userRepo.findByUserNo(userno);
        Project p = projectRepo.findById(projectId).get();

        if (p.getUser().contains(u)) {
        	p.getUser().remove(u);
        	u.getProjects().remove(p);
        	projectRepo.save(p);
        }
    }
	
	// 페이징
	public Page<ProjectResponse> paging(Pageable pageable) {
	    int page = pageable.getPageNumber(); // page 위치에 있는 값은 0부터 시작한다.
	    int pageLimit = 11; // 한 페이지에 보여줄 글 개수

	    Pageable p = PageRequest.of(page, pageLimit, Sort.by(Direction.DESC, "pid"));
	    Page<Project> projectPages = projectRepo.findAll(p);

	    Page<ProjectResponse> projectResponseDtos = projectPages.map(ProjectResponse::new);

	    return projectResponseDtos;
	}
	
	// 검색 결과 페이징
	public Page<ProjectResponse> pagingBySearch(Pageable pageable, List<Project> searchResult) {
	    int page = pageable.getPageNumber();
	    int pageLimit = 11;
	    int startIdx = page * pageLimit;
	    int endIdx = Math.min(startIdx + pageLimit, searchResult.size());

	    searchResult.sort(Comparator.comparing(Project::getPid).reversed()); 
	    List<Project> paginatedResults = searchResult.subList(startIdx, endIdx);
	    Page<ProjectResponse> pageResponse = new PageImpl<>(
	            paginatedResults.stream().map(ProjectResponse::new).collect(Collectors.toList()),
	            pageable,
	            paginatedResults.size() 
	            // 원래는 result.size()가 맞는데 이상하게 가끔 getTotalPages값이 1씩 크게 나옴
	            // 원인을 모르겠어서.. 임시 방편
	    );

	    return pageResponse;
	}
	
	// 검색
    public List<Project> searchProjects(int _process, int _type, String pname) {
        String process = "", type = "";
        
        switch (_process) {
        case 1:
            process = "개발 중";
            break;
        case 2:
            process = "개발 완료";
            break;
        }
        
        switch (_type) {
        case 1:
        	type = "팀";
        	break;
        case 2:
        	type = "개인";
        	break;
        }
        
    	Specification<Project> specification = ProjectSearchSpecification.findBySearch(process, type, pname);
        return projectRepo.findAll(specification);
    }
    
	// 리더 변경
	public void changeLeader(long pid, long userno) {
		Project p = projectRepo.findById(pid).get();
		p.setLeader(userno);
		projectRepo.save(p);
	}

}
