package com.ecommerce.personaldev.repositories;

import com.ecommerce.personaldev.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByCategoryName(@NotBlank @Size(min = '5' , message = "Length should be atleast 5") String categoryName);
}
