package com.npcweb.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.npcweb.dao.BoardDAO;
import com.npcweb.domain.Post;

@Repository
@Transactional
public class JpaBoardDAO implements BoardDAO {
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
}
