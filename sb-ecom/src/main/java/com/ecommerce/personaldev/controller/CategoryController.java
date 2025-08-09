package com.ecommerce.personaldev.controller;

import com.ecommerce.personaldev.model.Category;
import com.ecommerce.personaldev.service.CategoryService;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/public/categories")
    public List<Category> categories()
    {
        return categoryService.getallcategories();
    }

    @PostMapping("/api/admin/addcategories")
    public void addcategory(@RequestBody Category input)
    {
        categoryService.createCategory(input);
    }

    //For delete mappings as per the standards we only get the primary key in url with help of path variable.
    //For deletemappings don't use any request body
    @DeleteMapping("/api/admin/deletecategory/{id}")
    public void deletecategory(@PathVariable Long id)
    {
        categoryService.deleteCategory(id);
    }


}
