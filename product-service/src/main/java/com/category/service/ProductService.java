package com.category.service;

import com.category.DTO.ProductDTO;
import com.category.DTO.ProductListDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ProductService {
    public Map<String, Object> getProductById(Long pId,String token);

    Map<String, Object> save(ProductDTO productDTO,String token);

    Map<String, Object> updateProductById(Long pId, ProductDTO productDTO,String token);

    Map<String,Object> deleteProductById(Long pId, String token);

    Map<String, Object> uploadProductImage(Long pId, MultipartFile multipartFile,String token);
    Map<String, Object> search(String Text,String token);

    Map<String,Object> getproductList(ProductListDTO productListDTO,String token);
}
