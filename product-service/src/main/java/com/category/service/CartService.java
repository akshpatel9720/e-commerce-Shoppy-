package com.category.service;

import com.category.DTO.CartDTO;

import java.util.Map;

public interface CartService {
    Map<String, Object> save(CartDTO cartDTO);
}
