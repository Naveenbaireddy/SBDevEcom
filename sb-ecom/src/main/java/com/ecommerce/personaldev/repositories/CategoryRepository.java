package com.ecommerce.personaldev.repositories;

import com.ecommerce.personaldev.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
