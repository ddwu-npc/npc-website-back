package com.npcweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.npcweb.domain.UserProject;
import com.npcweb.domain.UserProjectId;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, UserProjectId> {
    List<UserProject> findByid_Pid(Long pid);
}