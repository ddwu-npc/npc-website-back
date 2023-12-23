package com.npcweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npcweb.dao.jpa.JpaDeptDAO;
import com.npcweb.repository.DeptRepository;

@Service
public class DeptService{
	@Autowired
	JpaDeptDAO deptDao;

	@Autowired
	DeptRepository deptRepo;
	
	public String findDnameByDeptno(int deptno) {
		return deptDao.findDnameByDeptno(deptno);
	}
	
}