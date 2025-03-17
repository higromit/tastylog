package com.RecipeCreator.tastylog;

import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TastylogApplication {

	public static void main(String[] args) {
		SpringApplication.run(TastylogApplication.class, args);
	}

}
