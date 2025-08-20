package com.ecommerce.personaldev.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    //Make sure you use wrapper classes as datatypes
    //If you use long instead of Long, during conversion ModelMapper sets the id to '0'  and no insertion happens since id is given to Categoryrepository.
    //long = Default value = 0 (cannot be null). Long = Default value = null (if not set).
    private Long categoryId;
    private String categoryName;
}
