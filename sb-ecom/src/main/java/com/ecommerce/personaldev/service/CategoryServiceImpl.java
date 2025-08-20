package com.ecommerce.personaldev.service;

import com.ecommerce.personaldev.exceptions.APIException;
import com.ecommerce.personaldev.exceptions.ResourceNotFoundException;
import com.ecommerce.personaldev.model.Category;
import com.ecommerce.personaldev.payload.CategoryDTO;
import com.ecommerce.personaldev.payload.CategoryResponse;
import com.ecommerce.personaldev.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
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
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getallcategories() {
        List<Category> categories=categoryRepository.findAll();
        if(categories.isEmpty()) {
            throw new APIException("No Category exists till now");
        }
        CategoryResponse categoryResponse= new CategoryResponse();
        List<CategoryDTO> categoryDTOS=categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO c) {
        //Should not save if already such a category exists
        Category category= categoryRepository.findByCategoryName(c.getCategoryName());
        if(category!=null)
            throw new APIException("Category with name "+c.getCategoryName()+" already exists");
        Category c1 = modelMapper.map(c,Category.class);
        categoryRepository.save(c1);
        return modelMapper.map(c,CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long id) {
        //To delete with id we converted the category list to a stream and filtered and if not found we are sending null.
        //If category is not found in delete you need to set response status as 404 "Category not found" and throw an error.
        Category c1= categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",id));
        categoryRepository.delete(c1);
        return modelMapper.map(c1,CategoryDTO.class) ;
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO updatedCategory, Long updatecategoryId) {

        Category c1=  categoryRepository.findById(updatecategoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",updatecategoryId));
        c1.setCategoryName(updatedCategory.getCategoryName());
        Category savedcategory1 = categoryRepository.save(c1);
        CategoryDTO savedcategoryDTO=modelMapper.map(savedcategory1,CategoryDTO.class);
        return savedcategoryDTO;
    }
}
