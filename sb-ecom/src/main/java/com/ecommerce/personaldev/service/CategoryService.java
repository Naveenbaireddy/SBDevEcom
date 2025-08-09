package com.ecommerce.personaldev.service;

import com.ecommerce.personaldev.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> getallcategories();
    void createCategory(Category c);

    String deleteCategory(Long id);

    Category updateCategory(Category updatedCategory, Long updatecategoryId);
}
