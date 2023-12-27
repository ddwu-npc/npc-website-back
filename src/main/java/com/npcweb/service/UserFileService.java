package com.npcweb.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.npcweb.dao.jpa.JpaUserFileDAO;
import com.npcweb.domain.User;
import com.npcweb.domain.UserFile;

@Service
public class UserFileService {
	@Autowired JpaUserFileDAO ufDao;
	
	@Value("${file.upload.path}")
	private String upPath;
	
	@Transactional
	public void submitFileUpload(MultipartFile uploadFile, User user) {
		
		if(uploadFile == null)
			return;

        try {
        	String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
            
            String uuid = UUID.randomUUID().toString();

            String savefileName = uuid + "_" + fileName;

            Path savePath = Paths.get(upPath + File.separator + savefileName);
            //Files.createDirectories(savePath.getParent());
        	uploadFile.transferTo(savePath);
            
        	UserFile uf = new UserFile();
        	uf.setFilePath(upPath);
        	uf.setOrgName(originalName);
        	uf.setsName(savefileName);
        	uf.setUser(user);
            
        	ufDao.insertFile(uf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	//read
	public UserFile readFile(long userno) {
		return ufDao.readFile(userno);
	}
	
	public void deleteFile(long userno) {
		ufDao.deleteFile(userno);
	}
}