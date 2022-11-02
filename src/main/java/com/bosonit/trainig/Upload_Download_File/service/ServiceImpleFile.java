package com.bosonit.trainig.Upload_Download_File.service;

import com.bosonit.trainig.Upload_Download_File.moldel.File;
import com.bosonit.trainig.Upload_Download_File.respository.FileRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@Service
public class ServiceImpleFile implements ServiceFile {

    @Autowired
    FileRespository fileRespository;


    private final Path root= Paths.get("uploads");

    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }



    @Override
    public File store(MultipartFile file, String type) throws Exception {
        String fileName2= StringUtils.cleanPath(file.getOriginalFilename().replace(" ",""));

        Path rutaArchivo= Paths.get("uploads").resolve(fileName2).toAbsolutePath();

        String fileType= StringUtils.cleanPath(file.getContentType());
        String[] splitfile = fileType.split("/");
        String arraysFile = splitfile[1];

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("files/").path(fileName2).toUriString();

        Files.copy(file.getInputStream(),rutaArchivo);
        File filedb= new File(fileName2,fileType,file.getBytes(),fileDownloadUri,LocalDateTime.now());

        return fileRespository.save(filedb);

    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file! " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public File getFile(String id) {
        return fileRespository.findById(id).get();
    }

    public Stream<File> getAllFiles() {
            return fileRespository.findAll().stream();
        }
}

