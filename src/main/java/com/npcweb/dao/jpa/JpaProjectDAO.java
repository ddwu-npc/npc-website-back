package com.npcweb.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class JpaProjectDAO {
	@PersistenceContext
	private EntityManager em;
	// 필요 시 추가
}
