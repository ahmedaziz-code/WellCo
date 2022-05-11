package com.springheroes.wellco;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;

import static com.springheroes.wellco.constant.FileConstant.USER_FOLDER;

@EnableWebMvc
@EnableScheduling
@SpringBootApplication
//@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
public class WellcoApplication {


	public static void main(String[] args) {

		SpringApplication.run(WellcoApplication.class, args);
	new File(USER_FOLDER).mkdirs();

	}
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
//	@Bean
//	CommandLineRunner init(StorageService storageService) {
//		return (args) -> {
//			storageService.deleteAll();
//			storageService.init();
//		};
//	}



}
