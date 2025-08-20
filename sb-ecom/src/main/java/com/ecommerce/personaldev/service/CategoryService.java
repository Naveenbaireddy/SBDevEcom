package com.ecommerce.personaldev.service;

import com.ecommerce.personaldev.model.Category;
import com.ecommerce.personaldev.payload.CategoryDTO;
import com.ecommerce.personaldev.payload.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    CategoryResponse getallcategories();
    CategoryDTO createCategory(CategoryDTO c);

    CategoryDTO deleteCategory(Long id);

    CategoryDTO updateCategory(CategoryDTO updatedCategory, Long updatecategoryId);
}
