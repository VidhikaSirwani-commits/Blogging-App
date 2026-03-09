package com.codemine.blog_app_apis.service;

import com.codemine.blog_app_apis.payloads.PostDto;
import com.codemine.blog_app_apis.payloads.PostResponse;

import java.util.List;

public interface PostService {

    // create posts
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    //update post
    PostDto updatePost(PostDto postDto, Integer PostId);

    //delete
    void deletePost(Integer postId);

    //get all posts
    //without pagination
//    List<PostDto> getAllPosts();
    //with pagination
//    List<PostDto> getAllPosts(Integer pageNumber, Integer pageSize);
    // with a PostResponse object
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize);

    //get single post
    PostDto getPostById(Integer postId);

    //get all post of a category
    //without Pagination and PostResponse
//    List<PostDto> getPostsByCategory(Integer categoryId);
//with Pagination and PostResponse
    PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize);

    //get all post of user
//    List<PostDto> getPostsByUser(Integer userId);
    //with pagination and postresponse
    PostResponse getPostsByUser(Integer userId,Integer pageNumber, Integer pageSize);

    //search post based on keyword
    List<PostDto> searchPosts(String keyword);
}
