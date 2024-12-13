package com.blog.application.services;

import com.blog.application.payloads.PostDto;
import com.blog.application.payloads.responses.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
    PostDto updatePost(PostDto postDto,Integer postId);
    void deletePost(Integer postId);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize);
    PostDto getPostById(Integer postId);
    List<PostDto> getPostsByCategory(Integer categoryId);
    List<PostDto> getAllPostsByUser(Integer userId);
    List<PostDto> getPostsBySearch(String keyword);
}
