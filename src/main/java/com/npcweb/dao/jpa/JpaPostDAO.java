package com.npcweb.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
	public void insertPost(Post post) throws DataAccessException {
		em.persist(post);
	}

	@Override
	public Post readPost(long post_id) throws DataAccessException {
		return em.find(Post.class, post_id);
	}

	@Override
	public void updatePost(Post post) throws DataAccessException {
		em.merge(post);
	}

	@Override
	public void deletePost(Post post) throws DataAccessException {
		em.remove(post);		
	}

	@Override
	public List<Post> getAllPost(long board_id) throws DataAccessException {
		TypedQuery<Post> query = em.createQuery(
				"select p from POST p where p.board_id = ?1", Post.class
			);
		query.setParameter(1, board_id);
		List<Post> posts = query.getResultList();
		
		return posts;
	}
}
