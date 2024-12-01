package com.blog.application.payloads;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ApiResponse {
	
	private String message;
	private boolean status;
	private LocalDate date;
	
	public ApiResponse(String message, boolean status) {
		super();
		this.message = message;
		this.status = status;
		this.date = LocalDate.now();
	}
	
}
