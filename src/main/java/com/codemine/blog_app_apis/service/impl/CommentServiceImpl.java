package com.codemine.blog_app_apis.service.impl;

import com.codemine.blog_app_apis.entities.Comment;
import com.codemine.blog_app_apis.entities.Post;
import com.codemine.blog_app_apis.entities.User;
import com.codemine.blog_app_apis.exceptions.ResourceNotFoundException;
import com.codemine.blog_app_apis.payloads.CommentDto;
import com.codemine.blog_app_apis.repository.CommentRepo;
import com.codemine.blog_app_apis.repository.PostRepo;
import com.codemine.blog_app_apis.repository.UserRepo;
import com.codemine.blog_app_apis.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment = commentRepo.save(comment);
        return modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
        commentRepo.delete(comment);
    }

    @Override
    public CommentDto createCommentV2(CommentDto commentDto, Integer postId, Integer userId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = commentRepo.save(comment);
        return modelMapper.map(savedComment,CommentDto.class);
    }
}
