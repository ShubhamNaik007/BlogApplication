package com.blog.application.controllers;

import com.blog.application.payloads.responses.ApiResponse;
import com.blog.application.payloads.CategoryDto;
import com.blog.application.payloads.responses.CategoryResponse;
import com.blog.application.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Validated @RequestBody CategoryDto categoryDto){
        CategoryDto createdCategory = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createdCategory,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Validated @RequestBody CategoryDto categoryData,@PathVariable("id") Integer categoryId){
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryData, categoryId);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("id") Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted successfully...!", true),HttpStatus.OK) ;
    }

    @GetMapping("/")
    public ResponseEntity<CategoryResponse> listOfCategories(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "3",required = false) Integer pageSize
    ){
        CategoryResponse allCategories = this.categoryService.getAllCategory(pageNumber,pageSize);
        return new ResponseEntity<>(allCategories,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Integer categoryId){
        CategoryDto category = this.categoryService.getCategory(categoryId);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }
}
