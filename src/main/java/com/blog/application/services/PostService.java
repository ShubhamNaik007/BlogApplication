package com.blog.application.services;

import com.blog.application.entities.Post;
import com.blog.application.payloads.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
    PostDto updatePost(PostDto postDto,Integer postId);
    void deletePost(Integer postId);
    List<PostDto> getAllPost();
    PostDto getPostById(Integer postId);
    List<PostDto> getPostsByCategory(Integer categoryId);
    List<PostDto> getAllPostsByUser(Integer userId);
    List<PostDto> getPostsBySearch(String keyword);
}
