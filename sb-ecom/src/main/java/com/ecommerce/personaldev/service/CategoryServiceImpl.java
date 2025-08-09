package com.ecommerce.personaldev.service;

import com.ecommerce.personaldev.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final List<Category> categories= new ArrayList<>();
    @Override
    public List<Category> getallcategories() {
        return categories;
    }

    @Override
    public void createCategory(Category c) {
        categories.add(c);
    }
}
