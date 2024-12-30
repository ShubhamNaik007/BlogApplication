package com.blog.application;

import com.blog.application.entities.Role;
import com.blog.application.repositories.RoleRepo;
import com.blog.application.utils.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}

	@Autowired
	private RoleRepo roleRepo;

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}


	@Override
	public void run(String... args) throws Exception {
		try {
			Role adminRole = new Role();
			adminRole.setId(AppConstants.ADMIN_USER);
			adminRole.setName("ADMIN_USER");

			Role normalRole = new Role();
			normalRole.setId(AppConstants.NORMAL_USER);
			normalRole.setName("NORMAL_USER");

			List<Role> roles = List.of(adminRole, normalRole);
			this.roleRepo.saveAll(roles);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
