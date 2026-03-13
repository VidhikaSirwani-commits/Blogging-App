package com.codemine.blog_app_apis.controllers;

import com.codemine.blog_app_apis.payloads.CommentDto;
import com.codemine.blog_app_apis.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/comment/{postId}")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable("postId") Integer postId
    ){
        CommentDto createdComment = commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Integer commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<String>("Comment Deleted successfully", HttpStatus.OK);
    }

    // add new feature where this comment is from which user
    @PostMapping("/comment/{postId}/{userId}")
    public ResponseEntity<CommentDto> createCommentV2(
            @RequestBody CommentDto commentDto,
            @PathVariable("postId") Integer postId,
            @PathVariable("userId") Integer userId
    ){
        CommentDto createdComment = commentService.createCommentV2(commentDto, postId, userId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }
}
