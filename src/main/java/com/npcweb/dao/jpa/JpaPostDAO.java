package com.npcweb.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.npcweb.dao.PostDAO;
import com.npcweb.domain.Post;

import java.util.List;
import javax.persistence.TypedQuery;

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

	@Override
	public long getBoardIdByPostId(long post_id) {
		System.out.println("getBoardId "+readPost(post_id).getBoardId());
		return readPost(post_id).getBoardId();
	}

	@Override
	public int getReadCountByPostId(long post_id) throws DataAccessException {
		return em.find(Post.class, post_id).getReadCount();
	}

	@Override
	public void updateReadCount(long post_id, int readCount) {
		Post p = em.find(Post.class, post_id);
		p.setReadCount(readCount);;
		em.merge(p);
	}
	
	@Override
	public List<Post> getUserPost (long userno) throws DataAccessException {
		TypedQuery<Post> query = em.createQuery(
			    "select p from Post p where p.userNo=:userno ORDER BY p.createDate DESC", Post.class
			);
		query.setParameter("userno", userno);
		List<Post> userPosts = query.getResultList();

		return userPosts;
	}
}
