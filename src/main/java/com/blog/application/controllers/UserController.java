package com.blog.application.controllers;

import com.blog.application.payloads.responses.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.blog.application.payloads.responses.ApiResponse;
import com.blog.application.payloads.UserDto;
import com.blog.application.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Validated @RequestBody UserDto userDto){
		UserDto createdUser = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
	}
	
	@GetMapping("/")
	public ResponseEntity<UserResponse> getUsers(
			@RequestParam(value = "pageNumber",defaultValue = "1",required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "3",required = false) Integer pageSize
	){
		UserResponse users = this.userService.getAllUsers(pageNumber,pageSize);
		return new ResponseEntity<>(users,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUser(@PathVariable Integer id){
		UserDto user =  this.userService.getUserById(id);
		
		return new ResponseEntity<UserDto>(user,HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> editUser(@Validated @RequestBody UserDto userDto,@PathVariable("id") Integer userId){
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return new ResponseEntity<UserDto>(updatedUser,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> removeUser(@PathVariable Integer id){
		this.userService.deleteUser(id);
		return new ResponseEntity(new ApiResponse("User Deleted Successfully", true),HttpStatus.OK);
	}
}
