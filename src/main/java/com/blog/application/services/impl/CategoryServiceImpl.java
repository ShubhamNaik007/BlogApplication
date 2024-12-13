package com.blog.application.services.impl;

import com.blog.application.entities.Category;
import com.blog.application.exceptions.ResourceNotFoundException;
import com.blog.application.payloads.CategoryDto;
import com.blog.application.payloads.responses.CategoryResponse;
import com.blog.application.payloads.responses.PostResponse;
import com.blog.application.payloads.responses.UserResponse;
import com.blog.application.repositories.CategoryRepo;
import com.blog.application.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category categoryData = this.categoryRepo.save(category);
        return this.modelMapper.map(categoryData,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = this.categoryRepo.save(category);
        return this.modelMapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
        this.categoryRepo.delete(category);
    }

    @Override
    public CategoryResponse getAllCategory(Integer pageNumber,Integer pageSize) {
        Pageable category = PageRequest.of(pageNumber,pageSize);
        Page<Category> categoryData = this.categoryRepo.findAll(category);
        List<Category> categories = categoryData.getContent();
        List<CategoryDto> categoriesDto = categories.stream().map(data-> this.modelMapper.map(data,CategoryDto.class)).toList();
        return categoryToCategoryResponse(categoryData,categoriesDto);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        return this.modelMapper.map(category,CategoryDto.class);
    }

    public CategoryResponse categoryToCategoryResponse(Page<Category> categoryData,List<CategoryDto> categoriesDto){
        CategoryResponse response = new CategoryResponse();
        response.setContent(categoriesDto);
        response.setPageNumber(categoryData.getNumber());
        response.setPageSize(categoryData.getSize());
        response.setTotalElements(categoryData.getTotalElements());
        response.setTotalPages(categoryData.getTotalPages());
        response.setLastPage(categoryData.isLast());
        return response;
    }
}
