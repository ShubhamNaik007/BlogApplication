package com.blog.application.services;

import com.blog.application.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
    void deleteCategory(Integer categoryId);
    List<CategoryDto> getAllCategory();
    CategoryDto getCategory(Integer categoryId);
}
