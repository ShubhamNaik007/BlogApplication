package com.blog.application.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceNotFoundException extends RuntimeException {
	
	String resourceName;
	String fieldName;
	int fieldValue;
	String fieldValueData;
	
	public ResourceNotFoundException(String resourceName,String fieldName,int fieldValue) {
		super(String.format("%s not found with %s : %d ",resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public ResourceNotFoundException(String resourceName,String fieldName,String fieldValueData) {
		super(String.format("%s not found with %s : %s ",resourceName,fieldName,fieldValueData));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValueData = fieldValueData;
	}
}
