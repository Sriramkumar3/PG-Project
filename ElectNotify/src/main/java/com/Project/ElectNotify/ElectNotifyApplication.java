package com.Project.ElectNotify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@EnableScheduling
public class ElectNotifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectNotifyApplication.class, args);
		
	}

}
