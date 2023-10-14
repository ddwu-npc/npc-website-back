package com.npcweb;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.npcweb.dao.jpa.JpaDeptDAO;
import com.npcweb.domain.Dept;


@SpringBootTest
class NpcWebApplicationTests {

    @Autowired
    private JpaDeptDAO deptDao;
    
    @PersistenceContext
    EntityManager em;

	@Test
	void contextLoads() {
		Dept dept = new Dept();
		dept.setDeptno(0);
		dept.setDname("test");
		
		deptDao.insertDept(dept);
		
        //User user = new User();
//        user.setBirthday(new Date());
//        user.setDeptNo(0);
//        user.setEmail("email");
//        user.setNickname("nickname");
//        user.setNpcPoint(0);
//        user.setPid(0);
//        user.setRank(0);
//        user.setRecentDate(new Date());
//        user.setUserId("id");
//        user.setUserNo(0);
//        user.setUserPw("pw");
//
//        userDao.insertUser(user);
//        // 엔티티 저장
//        em.persist(user);
	}
}
