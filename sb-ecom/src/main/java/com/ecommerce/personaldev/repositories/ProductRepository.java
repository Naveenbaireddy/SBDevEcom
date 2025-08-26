package com.ecommerce.personaldev.repositories;

import com.ecommerce.personaldev.model.Category;
import com.ecommerce.personaldev.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategoryOrderByPriceAsc(Category category);

    //Like means pattern matching to search name in all available ProductNames
    List<Product> findByProductNameLikeIgnoreCase(String keyword);
}
