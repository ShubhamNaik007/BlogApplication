package com.blog.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.application.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
}
