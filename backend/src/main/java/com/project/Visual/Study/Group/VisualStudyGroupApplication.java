package com.project.Visual.Study.Group;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VisualStudyGroupApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisualStudyGroupApplication.class, args);

		Dotenv dotenv = Dotenv.load();
		Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
		System.out.println("Cloudinary Cloud Name: " + cloudinary.config.cloudName);
	}
}

