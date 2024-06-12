package com.mail.mailSender.service.fileUpload;

import jakarta.mail.Multipart;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UploadFilesInterface {
    public Map upload(MultipartFile file, String folder) throws  Exception;
    public Map updateFile(MultipartFile file, String publicId);
    public Map deleteFile(String publicId);
}
