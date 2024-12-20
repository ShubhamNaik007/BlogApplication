package com.blog.application.services.impl;

import com.blog.application.entities.Comment;
import com.blog.application.entities.Post;
import com.blog.application.entities.User;
import com.blog.application.exceptions.ResourceNotFoundException;
import com.blog.application.payloads.CommentDto;
import com.blog.application.repositories.CommentRepo;
import com.blog.application.repositories.PostRepo;
import com.blog.application.repositories.UserRepo;
import com.blog.application.services.CommentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId",userId));

        Comment commentData = this.modelMapper.map(commentDto, Comment.class);
        commentData.setUser(user);
        commentData.setPost(post);
        commentData.setDateTime(LocalDateTime.now());
        Comment comment = commentRepo.save(commentData);

        return this.modelMapper.map(comment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        logger.info("commentId "+commentId);
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
        logger.info("comment "+comment.toString());
        this.commentRepo.delete(comment);
    }
}
