package com.mail.mailSender.service.fileUpload;

import com.cloudinary.Cloudinary;
import com.mail.mailSender.config.AppConfig;
import jakarta.mail.Multipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UploadFileService implements UploadFilesInterface{
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Map upload(MultipartFile file, String folder) throws Exception {
        try{
            Map<String, Object> options = Map.of("folder",folder);
            Map data = this.cloudinary.uploader().upload(file.getBytes(),options);
            return data;
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public Map updateFile(MultipartFile file, String publicId){
        try{
            Map<String, Object> options = Map.of("public_id",publicId);
            Map data = this.cloudinary.uploader().upload(file.getBytes(),options);
            return data;
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public Map deleteFile(String publicId){
        try{
            Map<String, Object> options = Map.of();
            return this.cloudinary.uploader().destroy(publicId,options);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
