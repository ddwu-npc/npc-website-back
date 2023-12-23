package com.npcweb.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
	
	public String findDnameByDeptno(int deptno) throws DataAccessException {
        Dept d = em.find(Dept.class, deptno);
        return d.getDname();
	}
	
	public int findDeptnoByDname(String dname) throws DataAccessException {
		TypedQuery<Integer> query = em.createQuery(
				"SELECT d.deptno FROM Dept d WHERE d.dname=:dname", Integer.class
			);
		query.setParameter("dname", dname);
		return query.getSingleResult();
        
	}
}
