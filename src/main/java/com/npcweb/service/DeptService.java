package com.npcweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npcweb.domain.Dept;
import com.npcweb.repository.DeptRepository;

@Service
public class DeptService{
	@Autowired
	DeptRepository deptRepo;

	public Dept findDept(int deptno) {
		return deptRepo.findById(deptno).get();
	}
	
	public Dept findDeptByDname(String dname) {
		return deptRepo.findByDname(dname);
	}
}