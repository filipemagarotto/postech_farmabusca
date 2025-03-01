package br.com.postech_farmabusca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PostechFarmabuscaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostechFarmabuscaApplication.class, args);
	}

}
