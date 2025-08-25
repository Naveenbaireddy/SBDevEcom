package com.ecommerce.personaldev.controller;


import com.ecommerce.personaldev.AppConstants;
import com.ecommerce.personaldev.payload.CategoryDTO;
import com.ecommerce.personaldev.payload.CategoryResponse;
import com.ecommerce.personaldev.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController
@RequestMapping(value="/api")
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> categories(@RequestParam(name="pageNumber",defaultValue = AppConstants.PAGE_NUMBER)Integer pageNumber,
                                                       @RequestParam(name="pageSize", defaultValue = AppConstants.PAGE_SIZE)Integer pageSize,
                                                       @RequestParam(name="sortBy",  defaultValue = AppConstants.SORT_CATEGORIES_BY) String sortBy,
                                                       @RequestParam(name="sortOrder",  defaultValue = AppConstants.SORT_DIR) String sortOrder)
    {
        CategoryResponse categoriesDTO=categoryService.getallcategories(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(categoriesDTO,HttpStatus.OK);
    }

    @PostMapping("/admin/addcategories")
    public ResponseEntity<CategoryDTO> addcategory(@Valid @RequestBody CategoryDTO input)
    {
        CategoryDTO savedCategory= categoryService.createCategory(input);
        return new ResponseEntity<>(savedCategory, HttpStatus.OK);
    }

    //For delete mappings as per the standards we only get the primary key in url with help of path variable.
    //For deletemappings don't use any request body
    //ResponseEntity is  used to wrap the response from ServiceImpl layer.

    @DeleteMapping("/admin/deletecategory/{id}")
    public ResponseEntity<CategoryDTO> deletecategory(@PathVariable Long id)
    {
            CategoryDTO deletedCategory= categoryService.deleteCategory(id);
            return new ResponseEntity<>(deletedCategory, HttpStatus.OK);
    }

    @PutMapping("/admin/updatecategory/{updatecategoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO UpdatedCategory,@PathVariable Long updatecategoryId )
    {
          CategoryDTO result=categoryService.updateCategory(UpdatedCategory,updatecategoryId);
          return new ResponseEntity<>(result,HttpStatus.OK);

    }

}
