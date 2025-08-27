package com.ecommerce.personaldev.repositories;

import com.ecommerce.personaldev.model.Category;
import com.ecommerce.personaldev.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageDetails);

    //Like means pattern matching to search name in all available ProductNames
    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageDetails);
}
