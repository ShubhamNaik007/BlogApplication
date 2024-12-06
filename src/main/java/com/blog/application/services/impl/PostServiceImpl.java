package com.blog.application.services.impl;

import com.blog.application.entities.Category;
import com.blog.application.entities.Post;
import com.blog.application.entities.User;
import com.blog.application.exceptions.ResourceNotFoundException;
import com.blog.application.payloads.PostDto;
import com.blog.application.repositories.CategoryRepo;
import com.blog.application.repositories.PostRepo;
import com.blog.application.repositories.UserRepo;
import com.blog.application.services.PostService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Post post =  this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(LocalDate.now());
        post.setCategory(category);
        post.setUser(user);
        Post savedPost = this.postRepo.save(post);
        return this.modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        PostDto postData = this.modelMapper.map(post,PostDto.class);
        postData.setTitle(postDto.getTitle());
        postData.setContent(postDto.getContent());
        postData.setImageName(postDto.getImageName());
        this.postRepo.save(post);
        return this.modelMapper.map(postData,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
        this.postRepo.deleteById(postId);
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> postsData = this.postRepo.findAll();
        List<PostDto> allPosts = postsData.stream().map((post)->this.modelMapper.map(post,PostDto.class)).toList();
        return allPosts;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post postData = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));
        return this.modelMapper.map(postData,PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category categoryData = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        List<Post> postsData = this.postRepo.findByCategory(categoryData);
        return postsData.stream().map(post -> this.modelMapper.map(post,PostDto.class)).toList();
    }

    @Override
    public List<PostDto> getAllPostsByUser(Integer userId) {
        User userData = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));
        List<Post> postsData = this.postRepo.findByUser(userData);
        return postsData.stream().map(post->this.modelMapper.map(post,PostDto.class)).toList();
    }

    @Override
    public List<PostDto> getPostsBySearch(String keyword) {
        return List.of();
    }
}
