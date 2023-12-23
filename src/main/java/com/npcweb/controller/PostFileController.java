package com.npcweb.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.npcweb.dao.jpa.JpaPostFileDAO;
import com.npcweb.domain.PostFile;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/file")
public class PostFileController {
	/*
	@Autowired JpaPostFileDAO pfDao;
	
	@PostMapping("/upload/{post_id}")
    public void submitFileUpload(@PathVariable long post_id, @RequestParam("file") MultipartFile[] uploadFiles) {
		for(MultipartFile file : uploadFiles){

            String originalName = file.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
            String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
            
            String uuid = UUID.randomUUID().toString();

            String savefileName = uploadPath + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(savefileName);

            try {
                file.transferTo(savePath);
                
                PostFile pf = new PostFile();
                pf.setFilePath(uploadPath);
                pf.setOrgName(originalName);
                pf.setsName(fileName);
                pf.setPostId(post_id);
                
                pfDao.insertFile(pf);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    */
}
