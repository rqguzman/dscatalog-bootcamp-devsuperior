package com.devsuperior.dscatalog;

import com.amazonaws.services.s3.transfer.Download;
import com.devsuperior.dscatalog.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class DscatalogApplication implements CommandLineRunner {

	@Autowired
	private S3Service s3Service;

	public static void main(String[] args) {
		SpringApplication.run(DscatalogApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String home = System.getProperty("user.home");
		File file = new File(home + File.separator + "Downloads" + File.separator + "EAStest_600x300.jpg");

		s3Service.uploadFile(file.toString());
	}
}
