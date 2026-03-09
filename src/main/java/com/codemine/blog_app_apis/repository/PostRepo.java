package com.codemine.blog_app_apis.repository;

import com.codemine.blog_app_apis.entities.Category;
import com.codemine.blog_app_apis.entities.Post;
import com.codemine.blog_app_apis.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {
// used without pagination
//    List<Post> findByCategory(Category category);

    // with pagination the method
    Page<Post> findByCategory(Category category, Pageable pageable);


    //without pagination
//    List<Post> findByUser(User user);

    //with pagination and PostResponse
    Page<Post> findByUser(User user,Pageable pageable);
}
