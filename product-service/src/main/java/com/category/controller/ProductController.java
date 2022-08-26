package com.category.controller;

import com.category.DTO.ProductDTO;
import com.category.entity.CategoryEntity;
import com.category.entity.ProductEntity;
import com.category.exception.ProductException;
import com.category.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/saveProduct")
    public ResponseEntity<Map<String, Object>> save(@RequestBody ProductDTO productDTO) {
        try {
            return new ResponseEntity<>(productService.save(productDTO), HttpStatus.OK);
        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            throw new ProductException.HandleException("data is not save");
        }
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<Map<String, Object>> updateProductById(@RequestParam("pId") Long pId, @RequestBody ProductDTO productDTO) {
        try {
            return new ResponseEntity<>(productService.updateProductById(pId, productDTO), HttpStatus.OK);
        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            throw new ProductException.HandleException("data is not save");
        }
    }

    @DeleteMapping("/deleteProductById")
    public ResponseEntity<Map<String, Object>> deleteCategoriesById(@RequestParam("pId") Long pId) {
        try {
            return new ResponseEntity<>(productService.deleteCategoriesById(pId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/uploadProductImage")
    public ResponseEntity<Map<String, Object>> uploadProfile(@RequestParam("pId") Long pId,
                                                             @RequestParam("img") MultipartFile multipartFile) {
        try {
            return new ResponseEntity<>(productService.uploadProductImage(pId, multipartFile), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/searchProduct")
    public ResponseEntity<Map<String, Object>> search(@RequestParam("Text") String Text) {
        try {
            return new ResponseEntity<>(productService.search(Text), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }




}
