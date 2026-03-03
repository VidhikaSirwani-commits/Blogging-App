package com.codemine.blog_app_apis.repository;

import com.codemine.blog_app_apis.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatergoryRepo extends JpaRepository<Category, Integer> {
}
