package com.blog.application.controllers;

import com.blog.application.exceptions.ResourceNotFoundException;
import com.blog.application.payloads.ApiResponse;
import com.blog.application.payloads.UserDto;
import com.blog.application.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;


import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper(); // Manually initialize ObjectMapper
    }

    @Test
    void createUser() throws Exception{
        UserDto userDto = new UserDto(1,"shubham","shubham@gmail.com","shubham","I am full stack developer");

        Mockito.when(userService.createUser(any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(post("/api/users/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("shubham"))
                .andExpect(jsonPath("$.password").value("shubham"));

        Mockito.verify(userService,Mockito.times(1)).createUser(any(UserDto.class));
    }

    @Test
    void getUser() throws Exception {
        UserDto userDto = new UserDto(1,"shubham","shubham@gmail.com","shubham","I am full stack developer");

        Mockito.when(userService.getUserById(anyInt())).thenReturn(userDto);

        mockMvc.perform(get("/api/users/1")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("shubham"))
                .andExpect(jsonPath("$.email").value("shubham@gmail.com"));

        Mockito.verify(userService,Mockito.times(1)).getUserById(anyInt());
    }

    @Test
    void updateUser() throws Exception{
        UserDto userDto = new UserDto(1,"shubham naik","shubham@gmail.com","shubham","I am full stack developer");

        Mockito.when(userService.updateUser(any(UserDto.class),anyInt())).thenReturn(userDto);

        mockMvc.perform(put("/api/users/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("shubham naik"));

        Mockito.verify(userService,Mockito.times(1)).updateUser(any(UserDto.class),anyInt());
    }

    @Test
    void deleteUser() throws Exception{
        Mockito.doNothing().when(userService).deleteUser(anyInt());

        mockMvc.perform(delete("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllUsers() throws Exception{
        UserDto userDto1 = new UserDto(1,"shubham naik","shubham@gmail.com","shubham","I am full stack developer");
        UserDto userDto2 = new UserDto(2,"shubham Shinde","shubham@gmail.com","shubham","I am backend developer");

        List<UserDto> usersData = Arrays.asList(userDto1,userDto2);

        Mockito.when(userService.getAllUsers()).thenReturn(usersData);

        mockMvc.perform(get("/api/users/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("shubham naik"))
                .andExpect(jsonPath("$[1].name").value("shubham Shinde"));
    }

    @Test
    void getAllUserEmptyList() throws Exception{
        List<UserDto> emptyList = new ArrayList<>();

        Mockito.when(userService.getAllUsers()).thenReturn(emptyList);

        mockMvc.perform(get("/api/users/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));

        Mockito.verify(userService,Mockito.times(1)).getAllUsers();
    }

}
