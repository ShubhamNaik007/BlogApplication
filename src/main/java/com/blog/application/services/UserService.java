package com.blog.application.services;

import java.util.List;

import com.blog.application.payloads.UserDto;
import com.blog.application.payloads.responses.UserResponse;

public interface UserService {
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer userId);
	UserDto getUserById(Integer userId);
	UserResponse getAllUsers(Integer pageNumber, Integer pageSize);
	void deleteUser(Integer userId);
	
}
