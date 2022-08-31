package com.category.service;

import com.category.DTO.CartDTO;
import com.category.DTO.CartListDTO;

import java.util.Map;

public interface CartService {
    Map<String, Object> save(CartDTO cartDTO, String token);

    Map<String, Object> deleteAProduct(Long userId, String pId, String token);

    Map<String, Object> deleteAllProduct(Long userId, String token);

    Map<String, Object> deleteSelectedProduct(Long userId, String pId, String token);

    Map<String, Object> getUser(CartListDTO cartListDTO, String token);
}
