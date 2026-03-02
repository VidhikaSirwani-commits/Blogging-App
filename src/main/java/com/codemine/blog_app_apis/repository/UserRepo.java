package com.codemine.blog_app_apis.repository;

import com.codemine.blog_app_apis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User, Integer> {
}
