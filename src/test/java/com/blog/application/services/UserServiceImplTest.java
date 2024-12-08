package com.blog.application.services;

import com.blog.application.entities.User;
import com.blog.application.exceptions.ResourceNotFoundException;
import com.blog.application.payloads.CategoryDto;
import com.blog.application.payloads.PostDto;
import com.blog.application.payloads.UserDto;
import com.blog.application.repositories.UserRepo;
import com.blog.application.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private ModelMapper modelMapper;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp(){
        System.out.println("setUp called");
        user = new User();
        user.setId(1);
        user.setName("Shubham");
        user.setEmail("shubham@gmail.com");
        user.setPassword("shubham");
        user.setAbout("I am full stack devloper");

        System.out.println("User "+user);

        userDto = new UserDto();
        userDto.setId(11);
        userDto.setName("Shubham");
        userDto.setEmail("shubham@gmail.com");
        userDto.setPassword("shubham");
        userDto.setAbout("I am full stack devloper");

        System.out.println("userDto "+userDto);
    }

    @Test
    void createUser_ShouldSaveAndReturnUser(){
        System.out.println("inside method");
        System.out.println("user "+user);
        Mockito.when(modelMapper.map(userDto,User.class)).thenReturn(user);
        Mockito.when(userRepo.save(user)).thenReturn(user);
        Mockito.when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);

        UserDto savedUser = userService.createUser(userDto);
        System.out.println(savedUser);

        assertNotNull(savedUser);
        assertEquals(savedUser.getName(),user.getName());

        Mockito.verify(userRepo,Mockito.times(1)).save(user);
    }

    @Test
    void updateUser_ShouldUpdateAndReturnUser(){
        Mockito.when(userRepo.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);

        UserDto updatedUser = userService.updateUser(userDto,1);

        assertNotNull(updatedUser);
        assertEquals(userDto.getName(),updatedUser.getName());

        Mockito.verify(userRepo,Mockito.times(1)).save(user);

    }

    @Test
    void updateUser_ShouldThrowException_WhenUserNotFound(){
        Mockito.when(userRepo.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> userService.updateUser(userDto,1));

        assertEquals("User",exception.getResourceName());
        assertEquals("id",exception.getFieldName());
        assertEquals(1,exception.getFieldValue());

        Mockito.verify(userRepo,Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        User user2 = new User();
        user2.setId(2);
        user2.setName("Jane Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setPassword("password123");
        user2.setAbout("About Jane");

        Mockito.when(userRepo.findAll()).thenReturn(Arrays.asList(user, user2));
        Mockito.when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        UserDto userDto2 = new UserDto();
        userDto2.setId(2);
        userDto2.setName("Jane Doe");
        userDto2.setEmail("jane.doe@example.com");
        userDto2.setPassword("password123");
        userDto2.setAbout("About Jane");

        Mockito.when(modelMapper.map(user2, UserDto.class)).thenReturn(userDto2);

        List<UserDto> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    void getUserById_ShouldReturnUser(){
        Mockito.when(userRepo.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);

        UserDto foundUser = userService.getUserById(1);

        assertNotNull(foundUser);
        assertEquals(userDto.getName(),foundUser.getName());

        Mockito.verify(userRepo,Mockito.times(1)).findById(1);
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserNotFound(){
        Mockito.when(userRepo.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> userService.getUserById(1));

        assertEquals("User",exception.getResourceName());
        assertEquals("Id",exception.getFieldName());
        assertEquals(1,exception.getFieldValue());

        Mockito.verify(userRepo, Mockito.times(1)).findById(1);
    }

    @Test
    void deleteUser_ShouldDeleteUser() {
        Mockito.when(userRepo.findById(1)).thenReturn(Optional.of(user));

        userService.deleteUser(1);

        Mockito.verify(userRepo, Mockito.times(1)).deleteById(1);
    }

    @Test
    void deleteUser_ShouldThrowException_WhenUserNotFound() {
        Mockito.when(userRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1));
        Mockito.verify(userRepo, Mockito.never()).deleteById(1);
    }
}
