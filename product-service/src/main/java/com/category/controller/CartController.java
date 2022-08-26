package com.category.controller;

import com.category.DTO.CartDTO;
import com.category.DTO.CategoryDTO;
import com.category.exception.ProductException;
import com.category.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/saveCart")
    public ResponseEntity<Map<String, Object>> save(@RequestBody CartDTO cartDTO) {
        try {
            return new ResponseEntity<>(cartService.save(cartDTO), HttpStatus.OK);
        } catch (Exception e) {
            throw new ProductException.HandleException("");
        }
    }
}
