package com.bosonit.trainig.Upload_Download_File.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface ServiceFile {

    public void init(); //ok

    public void save(MultipartFile file); //ok

    public Resource load(String filename); //ok

    public void deleteAll();

    public Stream<Path> loadAll();


}
