package com.npcweb.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.npcweb.dao.BoardDAO;
import com.npcweb.domain.Post;
import com.npcweb.domain.User;
import com.npcweb.service.UserService;

@Repository
@Transactional
public class JpaBoardDAO implements BoardDAO {
	@Autowired UserService us;
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getAllPost(long board_id) throws DataAccessException {
		String jpql = "SELECT p FROM Post p WHERE p.boardId = :boardId ORDER BY postId DESC";
        TypedQuery<Post> query = em.createQuery(jpql, Post.class);
        query.setParameter("boardId", board_id);
        return query.getResultList();
	}

	@Override
	public List<Post> getAllPostListByKeyword(long board_id, long rangeId, long searchRange, String text) throws DataAccessException {
		//board_id
		//rangeId : 전체0 임원1 팀장2
		//searchRange : 제목0 내용1 제목+내용2  작성자3
				
		String jpql = "";
		
		if(searchRange == 0)
			jpql = "SELECT p FROM Post p WHERE p.title LIKE :text AND p.boardId=:board_id";
		else if(searchRange == 1)
			jpql = "SELECT p FROM Post p WHERE p.content LIKE :text AND p.boardId=:board_id";
		else if(searchRange == 2)
			jpql = "SELECT p FROM Post p WHERE p.title LIKE :text OR p.content LIKE :text AND p.boardId=:board_id";
		else if(searchRange == 3) {
			jpql = "SELECT p FROM Post p WHERE p.userno = :userno AND p.boardId=:board_id";
		}
		
        TypedQuery<Post> query = em.createQuery(jpql, Post.class);
        if(searchRange <3)
        	query.setParameter("text", "%"+text+"%");
        else {
        	User user = us.getUserByNickname(text);
        	query.setParameter("userno", user.getUserNo());        	
        }
    	query.setParameter("board_id", board_id);
        
        
        return query.getResultList();
	}
}
