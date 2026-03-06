package com.codemine.blog_app_apis.repository;

import com.codemine.blog_app_apis.entities.Category;
import com.codemine.blog_app_apis.entities.Post;
import com.codemine.blog_app_apis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByCategory(Category category);

    List<Post> findByUser(User user);

}
