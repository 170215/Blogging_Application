package com.practice.DbinheritanceDemo.services;

import com.practice.DbinheritanceDemo.exceptions.ResourceNotFoundException;
import com.practice.DbinheritanceDemo.models.Post;
import com.practice.DbinheritanceDemo.payloads.CategoryDto;
import com.practice.DbinheritanceDemo.payloads.PostDto;
import com.practice.DbinheritanceDemo.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto,Integer user_id,Integer category_id) throws ResourceNotFoundException;
    PostDto updatePost(PostDto categoryDto,Integer id) throws ResourceNotFoundException;

    PostDto getPostById(Integer id) throws ResourceNotFoundException;

    void deletePost(Integer userId) throws ResourceNotFoundException;
    PostResponse getAllPosts(Integer page_no, Integer page_size,String sortBy,String sortDir);

    List<PostDto> getPostsByUser(Integer user_id) throws ResourceNotFoundException;
    List<PostDto> getPostsByCategory(Integer category_id) throws ResourceNotFoundException;

    List<PostDto> searchPosts(String keyword);

}
