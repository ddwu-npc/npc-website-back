package com.npcweb.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.npcweb.dao.CommentDAO;
import com.npcweb.domain.Comment;
import com.npcweb.domain.Post;

@Repository
@Transactional
public class JpaCommentDAO implements CommentDAO {
	@PersistenceContext private EntityManager em;
	
	@Override
	public void insertComment(Comment comment) throws DataAccessException {
		em.persist(comment);
	}

	@Override
	public void deleteComment(Comment comment) throws DataAccessException {
		em.remove(comment);
	}

	@Override
	public List<Comment> getAllComment(long post_id) throws DataAccessException {
		System.out.println("read comments of "+post_id);
		String jpql = "SELECT c FROM Comment c WHERE c.postId = :postId";
	    TypedQuery<Comment> query = em.createQuery(jpql, Comment.class);
	    query.setParameter("postId", post_id);
	    return query.getResultList();
	}

	@Override
	public Comment readComment(long commentId) {
		return em.find(Comment.class, commentId);
	}

}
