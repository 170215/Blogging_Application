package com.practice.DbinheritanceDemo.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {


    @Override
    public String uploadImage(String path, MultipartFile multipartFile) throws IOException {

        //filename
        String name = multipartFile.getOriginalFilename();
        String randomId= UUID.randomUUID().toString();
        String filename1=randomId+ name.substring(name.lastIndexOf("."));
        //fullpath
        String filePath = path+ File.separator+filename1;
        //create folder if not created
        File f= new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        Files.copy(multipartFile.getInputStream(), Paths.get(filePath));
        //cop
        return filename1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {

        String filePath= path+File.separator+fileName;
        InputStream is= new FileInputStream(filePath);
        //db logic to return input stream
        return is;
    }
}
