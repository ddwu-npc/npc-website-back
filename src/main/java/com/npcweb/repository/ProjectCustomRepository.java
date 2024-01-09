package com.npcweb.repository;

import org.springframework.stereotype.Repository;

import com.npcweb.domain.Project;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ProjectCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Project findProjectWithUsersAndAttendances(long projectId) {
        return entityManager.createQuery(
                "SELECT p FROM Project p JOIN FETCH p.users JOIN FETCH p.attendances WHERE p.pid = :projectId",
                Project.class)
            .setParameter("projectId", projectId)
            .getSingleResult();
    }
}
