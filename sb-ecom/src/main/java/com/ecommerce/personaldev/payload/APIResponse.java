package com.ecommerce.personaldev.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor
public class APIResponse {
    public String message;
    public Boolean status;
}
