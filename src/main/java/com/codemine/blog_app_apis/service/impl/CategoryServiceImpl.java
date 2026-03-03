package com.codemine.blog_app_apis.service.impl;

import com.codemine.blog_app_apis.entities.Category;
import com.codemine.blog_app_apis.exceptions.ResourceNotFoundException;
import com.codemine.blog_app_apis.payloads.CategoryDto;
import com.codemine.blog_app_apis.repository.CatergoryRepo;
import com.codemine.blog_app_apis.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    // performing crud for category class
    @Autowired
    private CatergoryRepo catergoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    //create
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = dtoToCategory(categoryDto);
        Category savedCategory = catergoryRepo.save(category);
        return categoryToDto(savedCategory);
    }

    //update
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId) {
        Category category=this.catergoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCat= catergoryRepo.save(category);
        return this.modelMapper.map(updatedCat, CategoryDto.class);
    }

    //delete
    @Override
    public String deleteCategory(Integer categoryId) {

        catergoryRepo.findById(categoryId)
                        .orElseThrow(()->new ResourceNotFoundException("Category","category id", categoryId));
        catergoryRepo.deleteById(categoryId);
        return "Category deleted with id"+categoryId;
    }

    //get
    @Override
    public CategoryDto fetchCategory(Integer categoryId) {
        Category category = catergoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "category id", categoryId));
        CategoryDto categoryDto=modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }

    //get-all
    @Override
    public List<CategoryDto> fetchAllCategory() {
        List<Category> allCats = catergoryRepo.findAll();
        List<CategoryDto> allCatsDto = allCats.stream().map(cat -> modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        return allCatsDto;
    }

    public Category dtoToCategory(CategoryDto categoryDto){
        Category category= this.modelMapper.map(categoryDto, Category.class);
        return category;
    }

    public CategoryDto categoryToDto(Category category){
        CategoryDto categoryDto= modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }
}
