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
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import com.npcweb.domain.PostFile;
import com.npcweb.service.PostFileService;
import com.npcweb.service.PostService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/files")
public class PostFileController {
	@Autowired PostFileService pfService;
	@Autowired PostService postService;
	@Value("${file.upload.path}")
	private String upPath;
	
	private static final Logger logger = LoggerFactory.getLogger(PostFileController.class);
	
	@GetMapping("/{post_id}")
    public List<PostFile> readFile(@PathVariable long post_id) {
		long haveFile = postService.readPost(post_id).getHavePostfile();
		
		if(haveFile == 1) {
			List<PostFile> pfList = pfService.readFile(post_id);
		    if (pfList != null)
		    	return pfList;
		    else
		    	return null;
		}
		return null;
    }
	
	@GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws IOException {
		System.out.println("fileName "+fileName);
		
		if(fileName == null || fileName.equals("undefined")) {
			System.out.println("fileName "+fileName);
			return null;
		}
		
		Path filePath = Paths.get(upPath).resolve(fileName).normalize();
        Resource resource;

        try {
            resource = new UrlResource(filePath.toUri());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

        String contentDisposition = "attachment; filename=" + resource.getFilename();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
	}
}
