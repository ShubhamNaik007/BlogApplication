package com.blog.application.services;

import com.blog.application.payloads.CategoryDto;
import com.blog.application.payloads.responses.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
    void deleteCategory(Integer categoryId);
    CategoryResponse getAllCategory(Integer pageNumber, Integer pageSize);
    CategoryDto getCategory(Integer categoryId);
}
