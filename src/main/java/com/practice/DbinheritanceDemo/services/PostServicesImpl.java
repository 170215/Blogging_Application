package com.practice.DbinheritanceDemo.services;

import com.practice.DbinheritanceDemo.exceptions.ResourceNotFoundException;
import com.practice.DbinheritanceDemo.models.Category;
import com.practice.DbinheritanceDemo.models.Post;
import com.practice.DbinheritanceDemo.models.User;
import com.practice.DbinheritanceDemo.payloads.PostDto;
import com.practice.DbinheritanceDemo.payloads.PostResponse;
import com.practice.DbinheritanceDemo.payloads.UserDto;
import com.practice.DbinheritanceDemo.repositories.CategoryRepository;
import com.practice.DbinheritanceDemo.repositories.PostRepository;
import com.practice.DbinheritanceDemo.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServicesImpl implements PostService{

    PostRepository postRepository;
    UserRepository userRepository;

    CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public PostServicesImpl(PostRepository postRepository, UserRepository userRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public  PostDto createPost(PostDto postDto,Integer user_id,Integer category_id) throws ResourceNotFoundException {

        User user =userRepository.findById(user_id).orElseThrow(()->new ResourceNotFoundException("User","user_id",user_id));
        Category category=categoryRepository.findById(category_id).orElseThrow(()->new ResourceNotFoundException("category","category_id",category_id));
        Post post1 =modelMapper.map(postDto, Post.class);
        post1.setImageName("default.png");
        post1.setAddedDate(new Date());
        post1.setUser(user);
        post1.setCategory(category);
        post1=postRepository.save(post1);
        return postToDto(post1);
    }

    @Override
    public  PostDto updatePost(PostDto postDto, Integer id) throws ResourceNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","post_id",id));
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setImageName(postDto.getImageName());
        post =postRepository.save(post);
        return postToDto(post);
    }

    @Override
    public  PostDto getPostById(Integer id) throws ResourceNotFoundException {
        Post post =postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","post_id",id));

        return postToDto(post);
    }

    @Override
    public void deletePost(Integer postId) throws ResourceNotFoundException {
        Post post =postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post_id",postId));
        postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer page_no, Integer page_size,String sortBy,String sortDir ) {
        Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page_no,page_size,sort);

        Page<Post> pages = postRepository.findAll(pageable);
        List<Post> posts=pages.getContent();
        List<PostDto>postDtos =posts.stream().map((element) -> modelMapper.map(element, PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pages.getNumber());
        postResponse.setPageSize(pages.getSize());
        postResponse.setTotalElements(pages.getTotalElements());
        postResponse.setTotalPages(pages.getTotalPages());
        postResponse.setLastPage(pages.isLast());
        return postResponse;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer user_id) throws ResourceNotFoundException {
        User user= userRepository.findById(user_id).orElseThrow(()->new ResourceNotFoundException("User","user_id",user_id));
        List<Post> posts=postRepository.findByUser(user);
        List<PostDto> postDtos =posts.stream().map((element) -> modelMapper.map(element, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer category_id) throws ResourceNotFoundException {
        Category category=categoryRepository.findById(category_id).orElseThrow(()->new ResourceNotFoundException("Category","category_id",category_id));
        List<Post> posts=postRepository.findByCategory(category);
        List<PostDto> postDtos=posts.stream().map(post->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts=postRepository.findByTitleContaining(keyword);
        List<PostDto> postDtos=posts.stream().map(post->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }



    public Post dtoToPost(PostDto post){

        return modelMapper.map(post,Post.class);
    }
    public PostDto postToDto(Post post){

        return modelMapper.map(post,PostDto.class);
    }



}
