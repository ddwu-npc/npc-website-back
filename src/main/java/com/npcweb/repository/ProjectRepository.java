package com.npcweb.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.npcweb.domain.Project;

public interface ProjectRepository  extends JpaRepository<Project, Long>{
    List<Project> findAll(Specification<Project> specification);
    
}
