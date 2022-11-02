package com.bosonit.trainig.Upload_Download_File.service;

import com.bosonit.trainig.Upload_Download_File.moldel.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface ServiceFile {

    public void init(); //ok
    public void deleteAll();
    public File store(MultipartFile file, String type) throws Exception;
    public Resource load(String filename);


    public File getFile(String id);
    public Stream<File> getAllFiles();


}
