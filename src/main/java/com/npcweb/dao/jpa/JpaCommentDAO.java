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
	public void updateComment(Comment comm) {
		em.merge(comm);
	}

	@Override
	public void deleteComment(long commentId) throws DataAccessException {
		Comment comm = em.find(Comment.class, commentId);
		em.remove(comm);
	}

	@Override
	public List<Comment> getAllComment(long post_id) throws DataAccessException {
		String jpql = "SELECT c FROM Comment c WHERE c.postId = :postId ORDER BY c.commentId";
	    TypedQuery<Comment> query = em.createQuery(jpql, Comment.class);
	    query.setParameter("postId", post_id);
	    return query.getResultList();
	}
	
	//게시글 삭제 시 해당 게시글의 모든 댓글 삭제
	@Override
	public void deleteCommentList(long post_id) {
		List<Comment> commList = getAllComment(post_id);
		
		if(commList != null)
			for(Comment e : commList)
				em.remove(e);
	}
	
	// 내가 쓴 댓글
	@Override
	public List<Comment> getUserComment (long userno) throws DataAccessException {
		TypedQuery<Comment> query = em.createQuery(
			    "select c from Comment c where c.userNo=:userno ORDER BY c.createDate DESC", Comment.class
			);
		query.setParameter("userno", userno);
		List<Comment> userComments = query.getResultList();

		return userComments;
	}

	public long findUserByCommentId(long commentId) {
		return em.find(Comment.class, commentId).getUserNo();
	}

}
