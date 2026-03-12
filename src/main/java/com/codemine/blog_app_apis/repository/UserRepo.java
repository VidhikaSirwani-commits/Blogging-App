package com.codemine.blog_app_apis.repository;

import com.codemine.blog_app_apis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepo extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.name like :name")
    List<User> searchByName(@Param("name") String name);

    List<User> findByNameContaining(String name);
}
