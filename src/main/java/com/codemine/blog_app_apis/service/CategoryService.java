package com.codemine.blog_app_apis.service;
import com.codemine.blog_app_apis.payloads.CategoryDto;
import com.codemine.blog_app_apis.payloads.CategoryResponse;
import com.codemine.blog_app_apis.payloads.PostResponse;

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
    //without pagination and PostResponse
//    List<CategoryDto> fetchAllCategory();

    // with pagination and PostResponse
    CategoryResponse fetchAllCategory(Integer pageNumber, Integer pageSize);
}
