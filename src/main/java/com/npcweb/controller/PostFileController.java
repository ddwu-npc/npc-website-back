package com.npcweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.domain.PostFile;
import com.npcweb.service.PostFileService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/files")
public class PostFileController {
	@Autowired PostFileService pfService;
	
	@GetMapping("/{post_id}")
    public PostFile readFile(@PathVariable long post_id) {
		
		System.out.println("readFile "+post_id);
		PostFile pf = pfService.readFile(post_id);
	    if (pf != null)
	    	return pf;
	    else
	    	return null;
	            
    }
}
