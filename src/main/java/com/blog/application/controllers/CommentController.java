package com.blog.application.controllers;

import com.blog.application.payloads.CommentDto;
import com.blog.application.payloads.responses.ApiResponse;
import com.blog.application.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @RequestParam(value = "userId",required = false) Integer userId,
            @RequestParam(value = "postId",required = false) Integer postId
    ){
        CommentDto comment = this.commentService.createComment(commentDto, userId, postId);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment deleted Successfully!!!",false),HttpStatus.OK);
    }
}
