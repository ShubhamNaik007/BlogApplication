package com.blog.application.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
	private int id;
	private String name;
	private String email;
	private String password;
	private String about;
}
