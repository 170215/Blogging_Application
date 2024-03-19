package com.practice.DbinheritanceDemo.controllers;

import com.practice.DbinheritanceDemo.exceptions.ResourceNotFoundException;
import com.practice.DbinheritanceDemo.models.Post;
import com.practice.DbinheritanceDemo.models.User;
import com.practice.DbinheritanceDemo.payloads.ApiResponse;
import com.practice.DbinheritanceDemo.payloads.CategoryDto;
import com.practice.DbinheritanceDemo.payloads.PostDto;
import com.practice.DbinheritanceDemo.payloads.PostResponse;
import com.practice.DbinheritanceDemo.repositories.CategoryRepository;
import com.practice.DbinheritanceDemo.repositories.PostRepository;
import com.practice.DbinheritanceDemo.repositories.UserRepository;
import com.practice.DbinheritanceDemo.services.CategoryService;
import com.practice.DbinheritanceDemo.services.FileService;
import com.practice.DbinheritanceDemo.services.PostService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    PostService postService;
    FileService fileService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    @Value("${project.image}")
    private String path;

    public PostController(PostService postService, PostRepository postRepository,
                          UserRepository userRepository,FileService fileService) {
        this.postService = postService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.fileService=fileService;
    }



    @PostMapping("/post/image/upload/{post_id}")
    public ResponseEntity<PostDto> fileUploadImage(@RequestParam("image") MultipartFile file,@PathVariable("post_id") Integer post_id) throws ResourceNotFoundException, IOException {
        String fileName = null;
        ApiResponse apiResponse=null;

            fileName = fileService.uploadImage(path,file);
            PostDto post = postService.getPostById(post_id);
            post.setImageName(fileName);
            PostDto postDto=postService.updatePost(post,post_id);
//            apiResponse = new ApiResponse(fileName + " image uploaded successfully", true);

        return ResponseEntity.ok(postDto);
    }

    @PostMapping("/user/{user_id}/category/{category_id}/posts")
    public ResponseEntity<PostDto> createTheCategory(@RequestBody PostDto postDto,@PathVariable("user_id")Integer user_id,@PathVariable("category_id")Integer category_id) throws ResourceNotFoundException {
        PostDto post1=postService.createPost(postDto,user_id,category_id);
        if(post1==null){
            System.out.println("its null");
        }
        return ResponseEntity.ok(post1);
    }
    @GetMapping("/user/{user_id}/posts/")
    public ResponseEntity<List<PostDto>> getPostbyTheUser(@PathVariable("user_id")Integer user_id) throws ResourceNotFoundException {
        List<PostDto> postDtos= postService.getPostsByUser(user_id);
        return ResponseEntity.ok(postDtos);
    }

    @GetMapping("/category/{category_id}/posts/")
    public ResponseEntity<List<PostDto>> getPostbyTheCategory(@PathVariable("category_id")Integer category_id) throws ResourceNotFoundException {
        List<PostDto> postDtos= postService.getPostsByCategory(category_id);
        return ResponseEntity.ok(postDtos);
    }

    @GetMapping("/posts/")
    public ResponseEntity<PostResponse> getTheAllPosts(
            @RequestParam(value="page_number",defaultValue ="0",required = false) Integer page_number,
            @RequestParam(value="page_size",defaultValue = "10",required = false) Integer page_size,
            @RequestParam(value="sortBy",defaultValue = "postid",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir){
        PostResponse postDtos=postService.getAllPosts(page_number,page_size,sortBy,sortDir);
        return ResponseEntity.ok(postDtos);

    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchThePosts(@PathVariable("keyword")String keyword){
        List<PostDto> postDtos=postService.searchPosts(keyword);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);

    }
    @PutMapping("/posts/{post_id}")
    public ResponseEntity<PostDto> updateThePost(@RequestBody PostDto postDto,@PathVariable("post_id") Integer post_id) throws ResourceNotFoundException {
        PostDto postDto1=postService.updatePost(postDto, post_id);
        return ResponseEntity.ok(postDto1);
    }
    @DeleteMapping ("/posts/{post_id}")
    public ResponseEntity<ApiResponse> DeleteThePost(@PathVariable("post_id") Integer post_id) throws ResourceNotFoundException {
        postService.deletePost(post_id);
        return new ResponseEntity<>(new ApiResponse("post deleted successfully",true),HttpStatus.OK);
    }

    //method to serve files
    @GetMapping(value = "/post/images/{image_name}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("image_name")String image_name, HttpServletResponse Response) throws IOException {

        InputStream resource = fileService.getResource(path, image_name);
        Response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,Response.getOutputStream());

    }

}
