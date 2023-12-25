package com.npcweb.dao.jpa;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
	
	public void deleteFile(long post_id) throws DataAccessException {
		TypedQuery<PostFile> query = em.createQuery(
				"SELECT pf FROM PostFile pf WHERE pf.post.postId=:post_id", PostFile.class
			);
		query.setParameter("post_id", post_id);		
		
		PostFile pf = query.getSingleResult();
		
		if(pf!=null) {
			try {
		        Path path = Paths.get(pf.getsName());
		        Files.deleteIfExists(path);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }finally {
		    	em.remove(pf);
		    }
		}else
			return;
	}

	public PostFile readFile(long post_id) {
		TypedQuery<PostFile> query = em.createQuery(
				"SELECT pf FROM PostFile pf WHERE pf.post.postId=:post_id", PostFile.class
			);
		query.setParameter("post_id", post_id);		
		
		PostFile pf = query.getSingleResult();
		
		if(pf!=null) {
			return pf;
		}else
			return null;
	}

}
