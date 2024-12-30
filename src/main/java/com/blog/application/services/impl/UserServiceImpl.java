package com.blog.application.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.blog.application.entities.Role;
import com.blog.application.payloads.responses.UserResponse;
import com.blog.application.repositories.RoleRepo;
import com.blog.application.utils.AppConstants;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.application.entities.User;
import com.blog.application.payloads.UserDto;
import com.blog.application.repositories.UserRepo;
import com.blog.application.services.UserService;
import com.blog.application.exceptions.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		//encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		//roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser = this.userRepo.save(user);
		return this.modelMapper.map(newUser,UserDto.class);
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userData, Integer userId) {
		User getUserData = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","id",userId));
		
		getUserData.setName(userData.getName());
		getUserData.setEmail(userData.getEmail());
		getUserData.setAbout(userData.getAbout());
		getUserData.setPassword(userData.getPassword());
		
		this.userRepo.save(getUserData);
		
		return this.userToDto(getUserData);
	}

	
	@Override
	public UserResponse getAllUsers(Integer pageNumber,Integer pageSize) {
		Pageable obj = PageRequest.of(pageNumber,pageSize);
		Page<User> userData =  this.userRepo.findAll(obj);
		List<User> users = userData.getContent();
		List<UserDto> usersData = users.stream().map(this::userToDto).collect(Collectors.toList());

		return userToUserResponse(userData,usersData);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user =  this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		return this.userToDto(user);
	}
	
	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		this.userRepo.deleteById(userId);
	}

	public UserResponse userToUserResponse(Page<User> pagePost, List<UserDto> userDto){
		UserResponse response = new UserResponse();
		response.setContent(userDto);
		response.setPageNumber(pagePost.getNumber());
		response.setPageSize(pagePost.getSize());
		response.setTotalElements(pagePost.getTotalElements());
		response.setTotalPages(pagePost.getTotalPages());
		response.setLastPage(pagePost.isLast());
		return response;
	}

	private User dtoToUser(UserDto userDto) {
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		return this.modelMapper.map(userDto,User.class);
	}
	
	public UserDto userToDto(User user) {
//		UserDto userDto = new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
		return this.modelMapper.map(user,UserDto.class);
	}
}
