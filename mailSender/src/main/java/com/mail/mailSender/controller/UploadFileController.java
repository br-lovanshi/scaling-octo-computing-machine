package com.mail.mailSender.controller;

import com.mail.mailSender.service.fileUpload.UploadFilesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("api/cloudinary/file")
public class UploadFileController {

    @Autowired
    private UploadFilesInterface uploadFileService;
    @PostMapping("/upload")
    public ResponseEntity<Map> uploadFile(@RequestParam("image")MultipartFile file) throws Exception{
        Map data = uploadFileService.upload(file,"utilityMailBodyImages");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Map> updateFile(@RequestParam("image") MultipartFile file, String publicId) throws Exception{
        Map data = uploadFileService.updateFile(file, publicId);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map> deleteFile(@RequestParam("publicId") String publicId){
        Map data = uploadFileService.deleteFile(publicId);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}