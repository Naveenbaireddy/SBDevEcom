package com.ecommerce.personaldev.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //File name of image uploaded by user
        String originalFileName=file.getOriginalFilename();

        //Generate a unique file name
        String randomId= UUID.randomUUID().toString();
        String fileName=randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        //File.separator → gives / on Linux/Mac, \ on Windows. Keeps code cross-platform.
        String filePath = path + File.separator + fileName;
        //check if path exists or not else create
        File folder = new File(path);
        if(!folder.exists())
        {
            folder.mkdir();
        }
        //upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));
        //return file name
        return fileName;
    }
}
