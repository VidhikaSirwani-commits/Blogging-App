package com.codemine.blog_app_apis.service;

import com.codemine.blog_app_apis.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer postId);
    void deleteComment(Integer commentId);

    CommentDto createCommentV2(CommentDto commentDto,Integer postId, Integer userId);
}
