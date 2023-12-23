package com.npcweb.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.npcweb.domain.PostFile;

@Repository
@Transactional
public class JpaPostFileDAO {
	@PersistenceContext
	private EntityManager em;
	
	public void insertFile(PostFile pf) throws DataAccessException {
		em.persist(pf);
	}
	
	public void deleteFile(PostFile pf) throws DataAccessException {
		em.remove(pf);
	}

}
