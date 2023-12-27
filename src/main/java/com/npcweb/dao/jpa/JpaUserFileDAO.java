package com.npcweb.dao.jpa;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.npcweb.domain.UserFile;

@Repository
@Transactional
public class JpaUserFileDAO {
	@PersistenceContext
	private EntityManager em;
	
	@Value("${file.upload.path}")
	private String upPath;
	
	public void insertFile(UserFile uf) {
		em.persist(uf);
	}
	
	public UserFile readFile(long userno) throws DataAccessException {
		TypedQuery<UserFile> query = em.createQuery(
				"SELECT uf FROM UserFile uf WHERE uf.user.userNo=:userno", UserFile.class
			);
		query.setParameter("userno", userno);		
	    try {
	    	UserFile uf = query.getSingleResult();
	    	return uf;
	    }catch (Exception ex){
	    	return null;
	    }
	}
	
	public void deleteFile(long userno) throws DataAccessException {
		TypedQuery<UserFile> query = em.createQuery(
				"SELECT uf FROM UserFile uf WHERE uf.user.userNo=:userno", UserFile.class
			);
		query.setParameter("userno", userno);		
		
	    try {
	    	UserFile uf = query.getSingleResult();
	        Path path = Paths.get(upPath + File.separator + uf.getsName());
	        Files.deleteIfExists(path);
	    	em.remove(uf);
	    }catch (Exception ex){
	    	return;
	    }
	}
	
}