package com.category.service;

import com.category.DTO.CartDTO;
import com.category.DTO.CartListDTO;

import java.util.Map;

public interface CartService {
    Map<String, Object> save(CartDTO cartDTO);

    Map<String, Object> deleteAProduct(CartDTO cartDTO);

    Map<String, Object> deleteAllProduct(Long userId);

    Map<String, Object> deleteSelectedProduct(CartDTO cartDTO);

    Map<String, Object> getUser(CartListDTO cartListDTO);
}
