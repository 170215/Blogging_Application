package com.practice.DbinheritanceDemo.services;

import com.practice.DbinheritanceDemo.exceptions.ResourceNotFoundException;
import com.practice.DbinheritanceDemo.models.Category;
import com.practice.DbinheritanceDemo.models.User;
import com.practice.DbinheritanceDemo.payloads.CategoryDto;
import com.practice.DbinheritanceDemo.payloads.UserDto;
import com.practice.DbinheritanceDemo.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository ;
    ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category =dtoToCategory(categoryDto);
        category =categoryRepository.save(category);

        return categoryToDto(category);
    }
    public Category dtoToCategory(CategoryDto categoryDto){
        return modelMapper.map(categoryDto,Category.class);
    }
    public CategoryDto categoryToDto(Category category){

        return modelMapper.map(category,CategoryDto.class);
    }
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("category","category_id",id));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category=categoryRepository.save(category);
        return categoryToDto(category);
    }

    @Override
    public CategoryDto getCategoryById(Integer id) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            return new ResourceNotFoundException("Category", "id", id);
        });
        return categoryToDto(category);
    }

    @Override
    public void deleteCategory(Integer userId) throws ResourceNotFoundException {

        Category category =categoryRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("Category","id",userId));
        categoryRepository.delete(category);

    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories=categoryRepository.findAll();

        List<CategoryDto> categoryDtos =categories.stream().map(category-> categoryToDto(category)).collect(Collectors.toList());
        return categoryDtos;
    }
}
