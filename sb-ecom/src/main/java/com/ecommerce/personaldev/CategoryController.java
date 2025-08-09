package com.ecommerce.personaldev;

import com.ecommerce.personaldev.model.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
public class CategoryController {

    private final List<Category> categories= new ArrayList<>();

    @GetMapping("/api/public/categories")
    public List<Category> categories()
    {
        return categories;
    }

    @PostMapping("/api/admin/addcategories")
    public String addcategory(@RequestBody Category input)
    {
        categories.add(input);
        return "success";
    }

}
