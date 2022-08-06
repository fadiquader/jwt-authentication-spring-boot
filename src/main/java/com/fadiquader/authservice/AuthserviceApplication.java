package com.fadiquader.authservice;

import com.fadiquader.authservice.domain.entity.RoleEntity;
import com.fadiquader.authservice.domain.entity.UserEntity;
import com.fadiquader.authservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.ArrayList;

@SpringBootApplication
public class AuthserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthserviceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return arg -> {
			userService.saveRole(new RoleEntity(null, "ROLE_USER"));
			userService.saveRole(new RoleEntity(null, "ROLE_MANAGER"));
			userService.saveRole(new RoleEntity(null, "ROLE_ADMIN"));
			userService.saveRole(new RoleEntity(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new UserEntity(null, "Fadi", "fadiquader", "123456", new ArrayList<>()));
			userService.saveUser(new UserEntity(null, "william", "william", "123456", new ArrayList<>()));
			userService.saveUser(new UserEntity(null, "alaa", "alaa", "123456", new ArrayList<>()));
			userService.saveUser(new UserEntity(null, "alan", "alan", "123456", new ArrayList<>()));

			userService.addRoleToUser("fadiquader", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("fadiquader", "ROLE_ADMIN");
			userService.addRoleToUser("fadiquader", "ROLE_USER");
			userService.addRoleToUser("william", "ROLE_MANAGER");
			userService.addRoleToUser("alaa", "ROLE_USER");
		};
	}
}
