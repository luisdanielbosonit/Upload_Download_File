package com.bosonit.trainig.Upload_Download_File;

import com.bosonit.trainig.Upload_Download_File.service.ServiceFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class UploadDownloadFileApplication implements CommandLineRunner {

	@Resource
	ServiceFile serviceFile;

	public static void main(String[] args) {
		SpringApplication.run(UploadDownloadFileApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		serviceFile.deleteAll();
		serviceFile.init();

	}
}
