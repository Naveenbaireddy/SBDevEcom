package com.ecommerce.personaldev.service;

import com.ecommerce.personaldev.exceptions.ResourceNotFoundException;
import com.ecommerce.personaldev.model.Category;
import com.ecommerce.personaldev.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    //private final List<Category> categories= new ArrayList<>();
    @Autowired
    private CategoryRepository categoryRepository;



    @Override
    public List<Category> getallcategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category c) {

        categoryRepository.save(c);
    }

    @Override
    public String deleteCategory(Long id) {
        //To delete with id we converted the category list to a stream and filtered and if not found we are sending null.
        //If category is not found in delete you need to set response status as 404 "Category not found" and throw an error.
        Category c1= categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",id));
        categoryRepository.delete(c1);
        return "Category deleted successfully" ;
    }

    @Override
    public Category updateCategory(Category updatedCategory, Long updatecategoryId) {

        Category c1=  categoryRepository.findById(updatecategoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",updatecategoryId));
        c1.setCategoryName(updatedCategory.getCategoryName());
        Category savedcategory1 = categoryRepository.save(c1);
        return savedcategory1;
    }
}
