package com.ecommerce.personaldev.controller;

import com.ecommerce.personaldev.model.Category;
import com.ecommerce.personaldev.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
@RestController
@RequestMapping(value="/api")
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> categories()
    {
        List<Category> categories=categoryService.getallcategories();
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

    @PostMapping("/admin/addcategories")
    public ResponseEntity<String> addcategory(@RequestBody Category input)
    {
        categoryService.createCategory(input);
        return new ResponseEntity<>("Category added successfully", HttpStatus.OK);
    }

    //For delete mappings as per the standards we only get the primary key in url with help of path variable.
    //For deletemappings don't use any request body
    //ResponseEntity is  used to wrap the response from ServiceImpl layer.

    @DeleteMapping("/admin/deletecategory/{id}")
    public ResponseEntity<String> deletecategory(@PathVariable Long id) {
        try {
            String s = categoryService.deleteCategory(id);
            return new ResponseEntity<>(s, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PutMapping("/admin/updatecategory/{updatecategoryId}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category UpdatedCategory,@PathVariable Long updatecategoryId )
    {
        try
        {
            Category result=categoryService.updateCategory(UpdatedCategory,updatecategoryId);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        catch(ResponseStatusException e){
            return new ResponseEntity<>(UpdatedCategory, e.getStatusCode());

    }

    }

}
