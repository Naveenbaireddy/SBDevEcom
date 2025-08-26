package com.ecommerce.personaldev.service;

import com.ecommerce.personaldev.exceptions.ResourceNotFoundException;
import com.ecommerce.personaldev.model.Category;
import com.ecommerce.personaldev.model.Product;
import com.ecommerce.personaldev.payload.ProductDTO;
import com.ecommerce.personaldev.payload.ProductResponse;
import com.ecommerce.personaldev.repositories.CategoryRepository;
import com.ecommerce.personaldev.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ModelMapper modelMapper;
    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
         Category category= categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
         Product product = modelMapper.map(productDTO,Product.class);
         product.setCategory(category);
         double specialPrice=product.getPrice()- (product.getDiscount()*0.01)*product.getPrice();
         product.setImage("Default.png");
         product.setSpecialPrice(specialPrice);
         Product savedProduct= productRepository.save(product);
         return modelMapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = products.stream().map(product ->modelMapper.map(product,ProductDTO.class)).collect(Collectors.toList());
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchProductsByCategory(Long categoryId) {
        Category category= categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOS = products.stream().map(product ->modelMapper.map(product,ProductDTO.class)).collect(Collectors.toList());
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchProductsByKeyword(String keyword) {
        //Pattern Matching is to be done with keyword on ProductName so we need to add "%"+"%"
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase("%"+keyword+"%");
        List<ProductDTO> productDTOS = products.stream().map(product ->modelMapper.map(product,ProductDTO.class)).collect(Collectors.toList());
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product productfromdb=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));
        Product product = modelMapper.map(productDTO,Product.class);
        productfromdb.setProductName(product.getProductName());
        productfromdb.setDescription(product.getDescription());
        productfromdb.setQuantity(product.getQuantity());
        productfromdb.setDiscount(product.getDiscount());
        productfromdb.setPrice(product.getPrice());
        productfromdb.setSpecialPrice(product.getSpecialPrice());
        Product savedProduct= productRepository.save(productfromdb);
        return modelMapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product productfromdb=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));
        productRepository.delete(productfromdb);
        return modelMapper.map(productfromdb,ProductDTO.class);
    }
}
