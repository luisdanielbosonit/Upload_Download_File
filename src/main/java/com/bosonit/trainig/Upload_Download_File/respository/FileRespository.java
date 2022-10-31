package com.bosonit.trainig.Upload_Download_File.respository;

import com.bosonit.trainig.Upload_Download_File.moldel.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface FileRespository extends JpaRepository<File,String> {

    Optional<File> findByName(String name);



}
