package com.mail.mailSender.service.fileUpload;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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


    public String getResourceType(String publicId){
        try {
            Map result = cloudinary.api().resource(publicId, ObjectUtils.emptyMap());
            return (String) result.get("resource_type");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching resource type: " + e.getMessage());
        }
    }
    @Override
    public Map upload(MultipartFile file, String folder) throws Exception {
        try{
            Map<String, Object> options = Map.of("folder",folder,"resource_type", "auto" );
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
            String resourceType = this.getResourceType(publicId);
            Map<String, Object> options = Map.of("resource_type", resourceType);
            return this.cloudinary.uploader().destroy(publicId,options);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
