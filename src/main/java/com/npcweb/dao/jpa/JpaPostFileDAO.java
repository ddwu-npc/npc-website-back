package com.npcweb.dao.jpa;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.npcweb.domain.PostFile;

@Repository
@Transactional
public class JpaPostFileDAO {
	@PersistenceContext
	private EntityManager em;
	
	@Value("${file.upload.path}")
	private String upPath;
	
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
		        Path path = Paths.get(upPath + File.separator + pf.getsName());
		        Files.deleteIfExists(path);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }finally {
		    	em.remove(pf);
		    }
		}else
			return;
	}

	public List<PostFile> readFile(long post_id) {
		TypedQuery<PostFile> query = em.createQuery(
				"SELECT pf FROM PostFile pf WHERE pf.post.postId=:post_id", PostFile.class
			);
		query.setParameter("post_id", post_id);		
		
		List<PostFile> pfList = query.getResultList();
		
		if(pfList!=null) {
			return pfList;
		}else
			return null;
	}

}
