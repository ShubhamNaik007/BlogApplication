package com.blog.application.services;

import com.blog.application.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Integer userId,Integer postId);
    void deleteComment(Integer commentId);
}
