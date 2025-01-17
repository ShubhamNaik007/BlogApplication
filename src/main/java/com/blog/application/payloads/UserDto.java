package com.blog.application.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;
	@NotEmpty
	@Size(min = 2,message = "username must be min of 2 characters !")
	private String name;
	@Email(message = "email id is not valid !")
	private String email;
	@NotEmpty(message = "password must be not empty")
	@Size(min = 2,message = "password must be minimum of 2 characters")
	private String password;
	@NotEmpty
	private String about;

	private Set<RoleDto> roles = new HashSet<>();
}
