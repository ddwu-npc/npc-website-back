package com.npcweb.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.npcweb.dao.jpa.JpaPostFileDAO;
import com.npcweb.domain.PostFile;

@Service
public class PostFileService {
	@Autowired JpaPostFileDAO pfDao;
	
	public void submitFileUpload(long post_id, MultipartFile uploadFile) {
		
		if(uploadFile == null)
			return;

        try {
        	String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
            String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
            
            String uuid = UUID.randomUUID().toString();

            String savefileName = uploadPath + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(savefileName);
            
        	uploadFile.transferTo(savePath);
            
            PostFile pf = new PostFile();
            pf.setFilePath(uploadPath);
            pf.setOrgName(originalName);
            pf.setsName(fileName);
            pf.setPostId(post_id);
            
            pfDao.insertFile(pf);
        } catch (IOException e) {
            e.printStackTrace();
        }
		/*
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
		*/
    }
	/*
	// 다운로드 API 구현 (파일명을 받아서 해당 파일을 응답으로 전송)
    @GetMapping("/download/{filename}")
    public ResponseEntity<PostFile> downloadFile(@PathVariable String filename) {
        // 파일 다운로드 로직을 구현
        // ...

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    */
}
