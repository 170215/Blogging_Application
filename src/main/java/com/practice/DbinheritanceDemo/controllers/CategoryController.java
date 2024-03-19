package com.practice.DbinheritanceDemo.controllers;

import com.practice.DbinheritanceDemo.exceptions.ResourceNotFoundException;
import com.practice.DbinheritanceDemo.models.Category;
import com.practice.DbinheritanceDemo.payloads.ApiResponse;
import com.practice.DbinheritanceDemo.payloads.CategoryDto;
import com.practice.DbinheritanceDemo.payloads.UserDto;
import com.practice.DbinheritanceDemo.repositories.CategoryRepository;
import com.practice.DbinheritanceDemo.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryService categoryService,
                              CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAlltheCategories(){

        List<CategoryDto> categoryDtos=categoryService.getAllCategories();
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> gettheCategoryById(@PathVariable("id")Integer id) throws ResourceNotFoundException {


        return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<CategoryDto> createTheCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1=categoryService.createCategory(categoryDto);
        return ResponseEntity.ok(categoryDto1);
    }

    @PutMapping("/{cat_id}")
    public ResponseEntity<CategoryDto> updateTheCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("cat_id") Integer id) throws ResourceNotFoundException {
        CategoryDto updatedCategory=categoryService.updateCategory(categoryDto,id);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);

    }
    @DeleteMapping ("/{cat_id}")
    public ResponseEntity<ApiResponse> deleteTheCategory(@PathVariable("cat_id") Integer id) throws ResourceNotFoundException {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(new ApiResponse("Category deleted successfully",true),HttpStatus.OK);

    }



}
