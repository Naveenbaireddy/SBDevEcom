package com.ecommerce.personaldev.model;

public class Category {
    private Long categoryId;
    private String CategoryName;

    public Category() {
    }

    public Category(Long categoryId, String CategoryName) {
        this.categoryId = categoryId;
        this.CategoryName = CategoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }
}