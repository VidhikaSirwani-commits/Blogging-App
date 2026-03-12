package com.codemine.blog_app_apis.service.impl;

import com.codemine.blog_app_apis.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceimpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //first get the filename
        String name = file.getOriginalFilename();
        //eg we got abc.png

        //adding the random id generated to append with the file
        String randomId= UUID.randomUUID().toString();
        String fileName1=randomId.concat(name.substring(name.lastIndexOf(".")));

        // we will get the full file path now along with path + filename
        String filePath=path+ File.separator +fileName1;

        //we will check if folder present or not. if not present then we will create new folder
        File f= new File(path);
        if (!f.exists()){
            f.mkdir();
        }

        //copy the file into our destination folder
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return name;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String filepath= path+File.separator+fileName;
        InputStream is=new FileInputStream(filepath);
        // can write db logic to return inputstream
        return is;
    }
}
