package com.npcweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npcweb.domain.Point;
import com.npcweb.repository.PointRepository;

@Service
public class PointService {
	@Autowired PointRepository pointRepo;
	
	public void insert(Point p) {
		pointRepo.save(p);
	}
	
	public Point findPoint(long point_id) {
		return pointRepo.findById(point_id).get();
	}
	
	public List<Point> findPointByUserno(long userno) {
		return pointRepo.findByUserno(userno);
	}
	
}
