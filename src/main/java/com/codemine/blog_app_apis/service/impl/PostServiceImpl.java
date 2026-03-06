package com.codemine.blog_app_apis.service.impl;

import com.codemine.blog_app_apis.entities.Category;
import com.codemine.blog_app_apis.entities.Post;
import com.codemine.blog_app_apis.entities.User;
import com.codemine.blog_app_apis.exceptions.ResourceNotFoundException;
import com.codemine.blog_app_apis.payloads.PostDto;
import com.codemine.blog_app_apis.repository.CatergoryRepo;
import com.codemine.blog_app_apis.repository.PostRepo;
import com.codemine.blog_app_apis.repository.UserRepo;
import com.codemine.blog_app_apis.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CatergoryRepo catergoryRepo;

    @Override
    public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {
        // first we will try to get the user and the category then set them
        // its like we are checking post belong to which user and category
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

        Category category = catergoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        // now we will try to set all the feilds
        //default dto feilds which we got from user is set using model mapper
        Post post=modelMapper.map(postDto, Post.class);

        //other feilds we will set which are not from the users
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post savedPost = postRepo.save(post);
        return modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post foundPost = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));
        foundPost.setTitle(postDto.getTitle());
        foundPost.setContent(postDto.getContent());

        return null;
    }

    @Override
    public void deletePost(Integer postId) {

    }

    @Override
    public List<PostDto> getAllPosts() {
        return List.of();
    }

    @Override
    public PostDto getPostById(Integer postId) {
        return null;
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category = catergoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", " category id", categoryId));
        List<Post> posts = postRepo.findByCategory(category);
        List<PostDto> postDtos = posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        List<Post> posts = postRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        return List.of();
    }
}
