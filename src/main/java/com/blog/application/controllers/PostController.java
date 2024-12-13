package com.blog.application.controllers;

import com.blog.application.payloads.responses.ApiResponse;
import com.blog.application.payloads.PostDto;
import com.blog.application.payloads.responses.PostResponse;
import com.blog.application.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId
    ){
        PostDto post = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber",defaultValue = "1",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize
    ){
        PostResponse allPost = this.postService.getAllPost(pageNumber,pageSize);
        return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post delete successfully...!",false),HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Integer postId){
        PostDto post = this.postService.getPostById(postId);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getAllPostsByCategory(@PathVariable Integer categoryId){
         List<PostDto> allPostsByCategory = this.postService.getPostsByCategory(categoryId);
         return new ResponseEntity<>(allPostsByCategory,HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getAllPostsByUser(@PathVariable Integer userId){
        List<PostDto> allPostsByUsers = this.postService.getAllPostsByUser(userId);
        return new ResponseEntity<>(allPostsByUsers,HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePostData(@RequestBody PostDto postData,@PathVariable Integer postId){
        PostDto updatedPost = this.postService.updatePost(postData,postId);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }
}
