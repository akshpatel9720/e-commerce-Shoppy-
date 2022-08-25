package com.category.service;

import com.category.entity.ProductEntity;

import java.util.Map;

public interface ProductService {
    public Map<String,Object> getProductById(Long pId);

    Map<String,Object> save(ProductEntity productEntity);
}
