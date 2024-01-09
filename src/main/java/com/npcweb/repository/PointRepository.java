package com.npcweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.npcweb.domain.Point;

public interface PointRepository extends JpaRepository<Point, Long>{
	List<Point> findByUserno(long userno);
	Point findByUsernoAndAttendanceId(long userNo, long attendanceId);

}
