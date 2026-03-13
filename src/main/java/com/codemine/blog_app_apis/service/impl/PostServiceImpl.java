package com.codemine.blog_app_apis.service.impl;

import com.codemine.blog_app_apis.entities.Category;
import com.codemine.blog_app_apis.entities.Post;
import com.codemine.blog_app_apis.entities.User;
import com.codemine.blog_app_apis.exceptions.ResourceNotFoundException;
import com.codemine.blog_app_apis.payloads.PostDto;
import com.codemine.blog_app_apis.payloads.PostResponse;
import com.codemine.blog_app_apis.repository.CatergoryRepo;
import com.codemine.blog_app_apis.repository.PostRepo;
import com.codemine.blog_app_apis.repository.UserRepo;
import com.codemine.blog_app_apis.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        foundPost.setImageName(postDto.getImageName());
        Post updatedPost = postRepo.save(foundPost);
        return this.modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        postRepo.delete(post);
    }

    //method 1 normal getAllPosts without Pagination
/*
    @Override
    public List<PostDto> getAllPosts(Integer pageNumber, Integer pageSize) {
        List<Post> allposts = postRepo.findAll();
        List<PostDto> postDtos = allposts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }
*/

    // method 2 with pagination
    /*
     @Override
    public List<PostDto> getAllPosts(Integer pageNumber, Integer pageSize) {

//        we will try to do pagination here
//        1. for findAll() we can pass a pageable object as well so we will create Pageable object
//        -> to create Pageable object we need method like PageRequest.of(num, size)
//        2. pass this Pageable object inside the findAll()
//        -> this will return Page<Post> type of objects
//        3. now to retrive the List<Post> we will extract the List in form of Pages
//        -> this is done by pagePost.getContent();
//        -> now using this we will get all Post in Page format


    //( we will make this pageNum and Size dynamic )
//        //number of pages
//        int pageNumber=2;
//        //number of records we want in 1 page
//        int pageSize=5;


    //first get a pageable type of object
    Pageable p = PageRequest.of(pageNumber, pageSize);
    //pass the pageable object inside findAll()
    Page<Post> postsPages = postRepo.findAll(p);
    //get the List from these posts using getContent()
    List<Post> allposts = postsPages.getContent();
    //now with this list convert to dtos

    List<PostDto> postDtos = allposts.stream()
            .map(post -> modelMapper.map(post, PostDto.class))
            .collect(Collectors.toList());


//        now rather than sending the list of posts we will send it in form of the PostResponse object
//        1. create a PostResponse object
//        2. start setting the data in here
//        3. send the PostResponse object back

        return postDtos;
}
*/


    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        // check which direction we need to sort
        /*
        Sort sort=null;
        if(sortBy.equalsIgnoreCase("asc"))
            //we are doing method chaining and trying to sort in the direction
            sort= Sort.by(sortBy).ascending();
        else
            sort= Sort.by(sortBy).descending();
*/
        //we can also go this using the ternary operator and avoid the if-else
        Sort sort= sortDir.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        //in pageable object we pass a sortBy object
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> postsPages = postRepo.findAll(p);
        List<Post> allposts = postsPages.getContent();
        List<PostDto> postDtos = allposts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        /*
        now rather than sending the list of posts we will send it in form of the PostResponse object
        1. create a PostResponse object
        2. start setting the data in here
        3. send the PostResponse object back
         */
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(postsPages.getNumber());
        postResponse.setPageSize(postsPages.getSize());
        postResponse.setTotalElements(postsPages.getNumberOfElements());
        postResponse.setTotalPages(postsPages.getTotalPages());
        postResponse.setLastPage(postsPages.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        PostDto postDto = modelMapper.map(post, PostDto.class);
        return postDto;
    }

    //without pagination and with the PostResponse
    /*
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
*/
    //change to pagination and with the PostResponse
    @Override
    public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        // perform sorting
        Sort sort=null;
        if (sortDir.equalsIgnoreCase("asc"))
            sort=Sort.by(sortBy).ascending();
        else
            sort=Sort.by(sortBy).descending();
        Category category = catergoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", " category id", categoryId));
        //trying to get the pageable object and pass into JPARepo
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        //get the page object
        Page<Post> posts = postRepo.findByCategory(category, pageable);
        //have to get all the posts from the page so use getContent()
        List<Post> allPosts = posts.getContent();
        //convert the list to dtos
        List<PostDto> postDtos = allPosts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        // create new PostResponse object and set the content
        PostResponse postResponse= new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLastPage(posts.isLast());
        return postResponse;
    }

    //without pagination and PostResponse
    /*
    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        List<Post> posts = postRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    } */

    //with pagination and PostResponse
    @Override
    public PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        Sort sort= sortDir.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
// get a pageable object
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        //from db get the Page object
        Page<Post> pagePost = postRepo.findByUserUserId(user.getUserId(), pageable);
        List<PostDto> postDtos =new ArrayList<>();
        for (Post post:pagePost.getContent()){
            System.out.println("--------------"+post);
            PostDto postDto=modelMapper.map(post,PostDto.class);
            postDtos.add(postDto);
        }
        // coverting the response to PostResponse
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
//        the way below we will use to get the like pattern %
//        (these are called wild cards % and _)
//        List<Post> resultPosts= postRepo.searchByTitle("%"+keyword+"%");
        List<Post> foundPosts = postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtos = foundPosts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }
}
