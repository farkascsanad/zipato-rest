package hu.csani.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZipatoRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipatoRestApplication.class, args);
	}

}
