package com.npcweb.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.npcweb.dao.PostDAO;
import com.npcweb.domain.Post;

@Repository
@Transactional
public class JpaPostDAO implements PostDAO {
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Post readPost(long post_id) throws DataAccessException {
		Post p = em.find(Post.class, post_id);
		return p;
	}

	@Override
	public void insertPost(Post post) throws DataAccessException {
		em.persist(post);
	}

	@Override
	public void updatePost(Post post) throws DataAccessException {
		em.merge(post);
	}

	@Override
	public void deletePost(Post post) throws DataAccessException {
		em.remove(post);
	}
}
