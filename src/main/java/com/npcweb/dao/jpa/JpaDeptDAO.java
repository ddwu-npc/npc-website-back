package com.npcweb.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.npcweb.domain.Dept;

@Repository
@Transactional
public class JpaDeptDAO {
	@PersistenceContext
    private EntityManager em;

	public void insertDept(Dept dept) throws DataAccessException {
		em.persist(dept);
	}

	public void updateDept(Dept dept) throws DataAccessException {
        em.merge(dept);
	}
}
