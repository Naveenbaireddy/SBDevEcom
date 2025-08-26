package com.ecommerce.personaldev.controller;


import com.ecommerce.personaldev.payload.ProductDTO;
import com.ecommerce.personaldev.payload.ProductResponse;
import com.ecommerce.personaldev.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("public/products")
    public ResponseEntity<ProductResponse> getAllProducts()
    {
        ProductResponse productresponse=  productService.getAllProducts();
        return new ResponseEntity<>(productresponse,HttpStatus.OK);

    }

    @GetMapping("public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword)
    {
        ProductResponse productResponse = productService.searchProductsByKeyword(keyword);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);

    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId)
    {
        ProductResponse productResponse = productService.searchProductsByCategory(categoryId);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @PostMapping("admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO, @PathVariable Long categoryId)
    {
        ProductDTO savedproductDTO= productService.addProduct(categoryId,productDTO);
        return new  ResponseEntity<>(savedproductDTO,HttpStatus.CREATED);
    }

    @PutMapping("admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long productId)
    {
        ProductDTO updatedProductDTO = productService.updateProduct(productId,productDTO);
        return new ResponseEntity<>(updatedProductDTO,HttpStatus.OK);
    }

    @DeleteMapping("admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId)
    {
        ProductDTO deletedDTO = productService.deleteProduct(productId);
        return new ResponseEntity<>(deletedDTO,HttpStatus.OK);
    }



}
