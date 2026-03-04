package com.Project.ElectNotify;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Project.ElectNotify.entity.Role;
import com.Project.ElectNotify.entity.User;
import com.Project.ElectNotify.repository.UserRepository;

@SpringBootApplication
@EnableScheduling
public class ElectNotifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectNotifyApplication.class, args);
		
	}
	
	//chumma
	@Bean
	CommandLineRunner run(UserRepository userRepository,
	                      PasswordEncoder passwordEncoder) {
	    return args -> {

	        if (userRepository.findByUsername("admin").isEmpty()) {

	            User admin = new User();
	            admin.setUsername("admin");
	            admin.setPassword(passwordEncoder.encode("admin123"));
	            admin.setRole(Role.ADMIN);

	            userRepository.save(admin);
	        }
	        
	        if (userRepository.findByUsername("blo").isEmpty()) {

	            User blo = new User();
	            blo.setUsername("blo");
	            blo.setPassword(passwordEncoder.encode("blo123"));
	            blo.setRole(Role.BLO);

	            userRepository.save(blo);
	        }
	    };
	}

}
