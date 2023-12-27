package com.npcweb.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import com.npcweb.domain.UserFile;
import com.npcweb.service.UserFileService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/userfile")
public class UserFileController {
	@Autowired UserFileService ufService;
	@Value("${file.upload.path}")
	private String upPath;
	
	@GetMapping("/{userno}")
    public UserFile readFile(@PathVariable long userno) {
		
		UserFile uf = ufService.readFile(userno);
	    if (uf != null)
	    	return uf;
	    else
	    	return null;
	            
    }
	
	
	
}