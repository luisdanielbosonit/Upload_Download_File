package com.bosonit.trainig.Upload_Download_File.controller;

import com.bosonit.trainig.Upload_Download_File.message.ResponseFile;
import com.bosonit.trainig.Upload_Download_File.message.ResponseMessage;
import com.bosonit.trainig.Upload_Download_File.moldel.File;
import com.bosonit.trainig.Upload_Download_File.respository.FileRespository;
import com.bosonit.trainig.Upload_Download_File.service.ServiceFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.*;
import java.util.stream.Collectors;

@RestController
public class FilesController {

    @Autowired
    ServiceFile serviceFile;

    @Autowired
    FileRespository fileRespository;



    @PostMapping("/upload/{type}")

    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file,@PathVariable String type) {

        String message = "";
        try {
            serviceFile.store(file,type);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }

    }
    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = serviceFile.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/id/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        File fileDB = serviceFile.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }


    @GetMapping("/files/name/{filename:.+}")
    public ResponseEntity<Resource> getFilebyName(@PathVariable String filename) {
        Resource file = serviceFile.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
