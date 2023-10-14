package com.npcweb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FILE")
public class File {
	@Id
	@Column(name="file_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	long fileId;
	
	@Column(name="post_id")
	long postId;
	
	@Column(name="file_type")
	String fileType;
	@Column(name="file_size")
	String fileSize;
	@Column(name="file_name")
	String fileName;
	
	// uid와 field는 일단 작업하지 않았어요
}
