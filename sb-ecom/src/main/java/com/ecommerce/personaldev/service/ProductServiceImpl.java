package com.ecommerce.personaldev.service;

import com.ecommerce.personaldev.exceptions.APIException;
import com.ecommerce.personaldev.exceptions.ResourceNotFoundException;
import com.ecommerce.personaldev.model.Category;
import com.ecommerce.personaldev.model.Product;
import com.ecommerce.personaldev.payload.ProductDTO;
import com.ecommerce.personaldev.payload.ProductResponse;
import com.ecommerce.personaldev.repositories.CategoryRepository;
import com.ecommerce.personaldev.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    ModelMapper modelMapper;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
         Category category= categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
         boolean productNotPresent=true;
         List<Product> products=category.getProducts();
         for(int i=0;i<products.size();i++)
         {
             if(products.get(i).getProductName().equals(productDTO.getProductName())) {
                 productNotPresent = false;
                 break;
             }
         }
         if(productNotPresent)
         {
             Product product = modelMapper.map(productDTO, Product.class);
             product.setCategory(category);
             double specialPrice = product.getPrice() - (product.getDiscount() * 0.01) * product.getPrice();
             product.setImage("Default.png");
             product.setSpecialPrice(specialPrice);
             Product savedProduct = productRepository.save(product);
             System.out.println(savedProduct.getProductName());
             return modelMapper.map(savedProduct, ProductDTO.class);
         }
         else{
             throw new APIException("Product Already Exists!!");
         }
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageDetails= PageRequest.of(pageNumber, pageSize,sortByAndOrder);
        Page<Product> pageProducts=productRepository.findAll(pageDetails);
        List<Product> products = pageProducts.getContent();
        List<ProductDTO> productDTOS = products.stream().map(product ->modelMapper.map(product,ProductDTO.class)).collect(Collectors.toList());
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse searchProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Category category= categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageDetails= PageRequest.of(pageNumber, pageSize,sortByAndOrder);
        Page<Product> pageProducts=productRepository.findByCategoryOrderByPriceAsc(category,pageDetails);
        List<Product> products = pageProducts.getContent();
        List<ProductDTO> productDTOS = products.stream().map(product ->modelMapper.map(product,ProductDTO.class)).collect(Collectors.toList());
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse searchProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageDetails= PageRequest.of(pageNumber, pageSize,sortByAndOrder);
        Page<Product> pageProducts=productRepository.findByProductNameLikeIgnoreCase("%"+keyword+"%",pageDetails);

        //Pattern Matching is to be done with keyword on ProductName so we need to add "%"+"%"
        List<Product> products=pageProducts.getContent();
        List<ProductDTO> productDTOS = products.stream().map(product ->modelMapper.map(product,ProductDTO.class)).collect(Collectors.toList());
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());
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

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        //Get the product from DB
        Product productfromdb=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));
        //Upload image to server(Which means images are going into a folder on the server where application is running)
        //Get the file name of image

        String fileName=fileService.uploadImage(path,image);
        productfromdb.setImage(fileName);
        //updated new file name to product.
        Product updatedproduct = productRepository.save(productfromdb);
        //convert and return product as DTO
        return modelMapper.map(updatedproduct,ProductDTO.class);
    }


}
