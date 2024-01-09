package com.npcweb.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.npcweb.dao.jpa.JpaPostFileDAO;
import com.npcweb.domain.Post;
import com.npcweb.domain.PostFile;

@Service
public class PostFileService {
	@Autowired JpaPostFileDAO pfDao;
	
	@Value("${file.upload.path}")
	private String upPath;
	
	@Transactional
	public void submitFileUpload(MultipartFile uploadFile, Post post) {
		
		if(uploadFile == null)
			return;

		try {
        	String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
            
            String uuid = UUID.randomUUID().toString();

            String savefileName = uuid + "_" + fileName;

            Path savePath = Paths.get(upPath + File.separator + savefileName);
        	uploadFile.transferTo(savePath);
            
            PostFile pf = new PostFile();
            pf.setFilePath(savePath.toString());
            pf.setOrgName(originalName);
            pf.setsName(savefileName);
            pf.setPost(post);
            
            pfDao.insertFile(pf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	//read
	public List<PostFile> readFile(long post_id) {
		return pfDao.readFile(post_id);
	}

	public void deleteFiles(long post_id) {
		pfDao.deleteFiles(post_id);
	}
}
