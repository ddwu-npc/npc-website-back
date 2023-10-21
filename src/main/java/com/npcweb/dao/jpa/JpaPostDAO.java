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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getAllPost(long board_id) throws DataAccessException {
		String jpql = "SELECT p FROM Post p WHERE p.boardId = :boardId";
        TypedQuery<Post> query = em.createQuery(jpql, Post.class);
        query.setParameter("boardId", board_id);
        return query.getResultList();
	}

	@Override
	public Post readPost(long post_id) throws DataAccessException {
		return em.find(Post.class, post_id);
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
