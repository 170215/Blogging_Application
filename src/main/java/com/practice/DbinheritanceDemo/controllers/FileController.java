package com.practice.DbinheritanceDemo.controllers;


import com.practice.DbinheritanceDemo.payloads.ApiResponse;
import com.practice.DbinheritanceDemo.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileController {

    FileService fileService;
    @Value("${project.image}")
    private String path;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
    @PostMapping("/upload/")
    public ResponseEntity<ApiResponse> fileUpload(@RequestParam("image")MultipartFile file){
        String fileName = null;
        ApiResponse apiResponse=null;
        try {
            fileName = fileService.uploadImage(path,file);
            apiResponse = new ApiResponse(fileName + " image uploaded successfully", true);
        } catch (IOException e) {
            e.printStackTrace();
            apiResponse = new ApiResponse( "image has not been uploaded ", false);
        }

        return ResponseEntity.ok(apiResponse);
    }
}
