package com.category.service;

import com.category.DTO.ProductDTO;
import com.category.DTO.ProductListDTO;
import com.category.entity.ProductEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ProductService {
    public Map<String, Object> getProductById(Long pId);

    Map<String, Object> save(ProductDTO productDTO,String token);

    Map<String, Object> updateProductById(Long pId, ProductDTO productDTO);

    Map<String,Object> deleteCategoriesById(Long pId);

    Map<String, Object> uploadProductImage(Long pId, MultipartFile multipartFile);
    Map<String, Object> search(String Text);

    Map<String,Object> getproductList(ProductListDTO productListDTO);
}
