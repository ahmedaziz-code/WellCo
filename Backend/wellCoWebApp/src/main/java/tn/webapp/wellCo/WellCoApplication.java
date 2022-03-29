package tn.webapp.wellCo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableScheduling
public class WellCoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WellCoApplication.class, args);
	}

}
