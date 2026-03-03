package com.codemine.blog_app_apis.service;

import com.codemine.blog_app_apis.entities.Category;
import com.codemine.blog_app_apis.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    //in interface all methods are public
    //create
    CategoryDto createCategory(CategoryDto categoryDto);

    //update
    CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);

    //delete
    String deleteCategory(Integer categoryId);

    //get
    CategoryDto fetchCategory(Integer categoryId);

    //get-all
    List<CategoryDto> fetchAllCategory();
}
