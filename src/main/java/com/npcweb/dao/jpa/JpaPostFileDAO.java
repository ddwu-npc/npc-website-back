package com.npcweb.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.npcweb.domain.Post;
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

	public PostFile readFile(long post_id) {
		TypedQuery<PostFile> query = em.createQuery(
				"SELECT pf FROM PostFile pf WHERE pf.post.postId=:post_id", PostFile.class
			);
		query.setParameter("post_id", post_id);		
		
		PostFile pf = query.getSingleResult();
		
		if(pf!=null) {
			System.out.println("pf 찾음 "+pf.getOrgName());
			return pf;
		}else
			return null;
	}

}
