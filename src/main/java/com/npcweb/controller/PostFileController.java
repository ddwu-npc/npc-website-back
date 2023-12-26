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

import com.npcweb.domain.PostFile;
import com.npcweb.service.PostFileService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/files")
public class PostFileController {
	@Autowired PostFileService pfService;
	@Value("${file.upload.path}")
	private String upPath;
	
	@GetMapping("/{post_id}")
    public PostFile readFile(@PathVariable long post_id) {
		PostFile pf = pfService.readFile(post_id);
	    if (pf != null)
	    	return pf;
	    else
	    	return null;
	            
    }
	
	// 다운로드 API 구현 (파일명을 받아서 해당 파일을 응답으로 전송)
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws IOException {
    	System.out.println("download "+fileName);
    	
    	Path filePath = Paths.get(upPath).resolve(fileName);
       
    	// 파일이 존재하는지 확인
        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }
        
        // 파일 이름을 UTF-8로 인코딩
        //String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        
        // 파일을 읽어 InputStream 생성
        InputStream inputStream = Files.newInputStream(filePath);

        // 파일 다운로드를 위한 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        //headers.add(HttpHeaders.CONTENT_TYPE, "image/jpg");
        
        // InputStreamResource를 사용하여 ResponseEntity 생성
        InputStreamResource resource = new InputStreamResource(inputStream);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(Files.size(filePath))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
