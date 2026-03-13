package com.codemine.blog_app_apis.repository;

import com.codemine.blog_app_apis.entities.Category;
import com.codemine.blog_app_apis.entities.Post;
import com.codemine.blog_app_apis.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {
// used without pagination
//    List<Post> findByCategory(Category category);

    // with pagination the method
    Page<Post> findByCategory(Category category, Pageable pageable);


    //without pagination
//    List<Post> findByUser(User user);

    //with pagination and PostResponse
    Page<Post> findByUserUserId(Integer userId, Pageable pageable);

    /*
    when coding the programer was facing an issue because of the version had been mismatched
    so its like he gave a Query to get the same thing which the below method is performing
    1. we are giving @Query and then trying to give dynamic value to it using :key
    2. to set the dynamic value we using @Param
    3. inside our service method we will pass the %keyword% because this is like Query
     */
    @Query("select p from Post p where p.title like :key")
    List<Post> searchByTitle(@Param("key") String title);


    List<Post> findByTitleContaining(String title);
}
