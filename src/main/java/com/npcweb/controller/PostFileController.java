package com.npcweb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import com.npcweb.domain.PostFile;
import com.npcweb.service.PostFileService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/files")
public class PostFileController {
	@Autowired PostFileService pfService;
	@Value("${file.upload.path}")
	private String upPath;
	
	private static final Logger logger = LoggerFactory.getLogger(PostFileController.class);
	
	@GetMapping("/{post_id}")
    public PostFile readFile(@PathVariable long post_id) {
		PostFile pf = pfService.readFile(post_id);
	    if (pf != null)
	    	return pf;
	    else
	    	return null;
	            
    }
	
	@GetMapping("/download/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String fileName) throws IOException {
		//System.out.println("fileName "+fileName+" "+post_id);
		
		//PostFile pf = readFile(post_id);
		
		try {
			//Path filePath = Paths.get(upPath, String.valueOf(post.getStudy().getId()), post.getTileUrl());
			Path filePath = Paths.get(upPath).resolve(fileName);
			
			Resource resource = new UrlResource(filePath.toUri());
			InputStreamResource inputStreamResource = new InputStreamResource(resource.getInputStream());
			
			System.out.println("크기 "+Files.size(filePath));
			System.out.println("resource "+resource);
	        //logger.info("File content: {}", Arrays.toString(Files.readAllBytes(filePath)));
			System.out.println("getFilename() "+resource.getFilename());
			System.out.println("attachment; filename=\""+resource.getFilename()+"\"");
			/*
			HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", pf.getOrgName());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            // ResponseEntity 반환
            return new ResponseEntity<>(inputStreamResource, headers, HttpStatus.OK);
			*/
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
	                .contentLength(resource.contentLength())
	                .header(HttpHeaders.CONTENT_ENCODING, "identity") 
	                .body(inputStreamResource);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/*
	// 다운로드 API 구현 (파일명을 받아서 해당 파일을 응답으로 전송)
    @GetMapping("/download/{fileName}")
    public void downloadFile(@PathVariable String fileName,HttpServletResponse response) throws IOException {
    	logger.info("download {}", fileName);
    	
    	Path filePath = Paths.get(upPath).resolve(fileName);
    	
    	if (!Files.exists(filePath)) {
            System.out.println("File not found: " + fileName);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    	
    	//String contentType = Files.probeContentType(f.toPath());
    	String contentType = determineContentType(fileName);
    	response.setContentType(contentType);
    	//response.setContentType("application/octet-stream"); // 또는 다른 적절한 MIME 타입
    	response.setContentLength((int) Files.size(filePath));
    	response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
       
        System.out.println("크기 "+Files.size(filePath));
        System.out.println("content type "+response.getContentType());
        //logger.info("File content: {}", Arrays.toString(Files.readAllBytes(filePath)));
        
        try (OutputStream os = response.getOutputStream(); InputStream is = Files.newInputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            //String responseData = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
            //System.out.println("Response Data: " + responseData);
        } catch (IOException e) {
            logger.error("Error during file download", e);
            throw e;
        }
    }
    
    private String determineContentType(String fileName) {
        // 파일 확장자에 따라 Content-Type 설정
        String extension = FilenameUtils.getExtension(fileName);
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            // 다른 파일 형식에 대한 처리 추가
            default:
                return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
    }
    */
}
