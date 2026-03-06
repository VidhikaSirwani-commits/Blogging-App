package com.codemine.blog_app_apis.service;

import com.codemine.blog_app_apis.entities.Post;
import com.codemine.blog_app_apis.payloads.PostDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface PostService {

    // create posts
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    //update post
    PostDto updatePost(PostDto postDto, Integer PostId);

    //delete
    void deletePost(Integer postId);

    //get all posts
    List<PostDto> getAllPosts();

    //get single post
    PostDto getPostById(Integer postId);

    //get all post of a category
    List<PostDto> getPostsByCategory(Integer categoryId);

    //get all post of user
    List<PostDto> getPostsByUser(Integer userId);

    //search post based on keyword
    List<PostDto> searchPosts(String keyword);
}
