package com.category.controller;

import com.category.entity.CategoryEntity;
import com.category.entity.ProductEntity;
import com.category.exception.ProductException;
import com.category.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/getProductById")
    public ResponseEntity<Map<String, Object>> getCategoryById(@RequestParam("pId") Long pId) {
        try {
            return new ResponseEntity<>(productService.getProductById(pId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ProductException.HandleException("product is not find");
        }
    }

    @PostMapping("/saveCategory")
    public ResponseEntity<Map<String, Object>> save(@RequestBody ProductEntity productEntity) {
        try {
            return new ResponseEntity<>(productService.save(productEntity), HttpStatus.OK);
        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            throw new ProductException.HandleException("data is not save");
        }
    }
}
