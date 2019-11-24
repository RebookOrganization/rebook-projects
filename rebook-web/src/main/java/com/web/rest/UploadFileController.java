package com.web.rest;

import com.web.service.FileStorageService;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api")
public class UploadFileController {

  private Logger logger = LoggerFactory.getLogger(UploadFileController.class);

  @Autowired
  private FileStorageService fileStorageService;

  @PostMapping("/uploadFile")
  public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
    return new ResponseEntity<>(fileStorageService.uploadFile(file), HttpStatus.OK);
  }

  @PostMapping("/uploadMultipleFiles")
  public ResponseEntity<?> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
    return new ResponseEntity<>(fileStorageService.uploadMultipleFiles(files), HttpStatus.OK);
  }

  @GetMapping("/downloadFile/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
    return fileStorageService.downloadFile(fileName, request);
  }

  @GetMapping("/getImage/{fileName:.+}")
  public ResponseEntity<?> getImageFile(@PathVariable String fileName) {
    return new ResponseEntity<>(fileStorageService.loadFileAsResource(fileName), HttpStatus.OK);
  }
}
