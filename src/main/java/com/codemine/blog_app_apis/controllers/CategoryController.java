package com.codemine.blog_app_apis.controllers;

import com.codemine.blog_app_apis.payloads.CategoryDto;
import com.codemine.blog_app_apis.payloads.CategoryResponse;
import com.codemine.blog_app_apis.payloads.PostResponse;
import com.codemine.blog_app_apis.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/apis/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    //create
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createdCategory, HttpStatus.CREATED);
    }

    //update
    @PutMapping
    public ResponseEntity<CategoryDto> updatedCategory(@Valid @RequestBody CategoryDto categoryDto, @RequestParam Integer categoryId){
        CategoryDto updatedCatDto = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<CategoryDto>(updatedCatDto,HttpStatus.OK);
    }

    //delete
    @DeleteMapping
    public ResponseEntity<String> deleteCategory(@RequestParam Integer categoryId){
        String response = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<String>(response,HttpStatus.OK);
    }

    //get
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable(name = "id") Integer categoryId){
        CategoryDto foundCategory = categoryService.fetchCategory(categoryId);
        return new ResponseEntity<CategoryDto>(foundCategory,HttpStatus.OK);
    }

    //get-all
    //without pagination and PostResponse
//    @GetMapping
//    public ResponseEntity<List<CategoryDto>> getAllCategories(){
//        List<CategoryDto> allCategoryDtos = categoryService.fetchAllCategory();
//        return new ResponseEntity<List<CategoryDto>>(allCategoryDtos,HttpStatus.OK);
//    }

    //with pagination and PostResponse
    @GetMapping
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "sortBy", required = false)String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "sortDir", required = false)String sortDir
    ){
        CategoryResponse allCategoriesDtos= categoryService.fetchAllCategory(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<CategoryResponse>(allCategoriesDtos,HttpStatus.OK);
    }

}
