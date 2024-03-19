package com.practice.DbinheritanceDemo.services;

import com.practice.DbinheritanceDemo.exceptions.ResourceNotFoundException;
import com.practice.DbinheritanceDemo.payloads.CategoryDto;
import com.practice.DbinheritanceDemo.payloads.UserDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto,Integer id) throws ResourceNotFoundException;

    CategoryDto getCategoryById(Integer id) throws ResourceNotFoundException;

    void deleteCategory(Integer userId) throws ResourceNotFoundException;
    List<CategoryDto> getAllCategories();
}
