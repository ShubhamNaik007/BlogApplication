package com.blog.application.services;

import com.blog.application.payloads.UserDto;
import com.blog.application.payloads.responses.UserResponse;

public interface UserService {
	UserDto registerNewUser(UserDto user);
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer userId);
	UserDto getUserById(Integer userId);
	UserResponse getAllUsers(Integer pageNumber, Integer pageSize);
	void deleteUser(Integer userId);
}
