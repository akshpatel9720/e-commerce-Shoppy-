package com.category.controller;

import com.category.DTO.CartDTO;
import com.category.DTO.CategoryDTO;
import com.category.exception.ProductException;
import com.category.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"data not saved");
        }
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<Map<String,Object>> deleteProduct(@RequestBody CartDTO cartDTO){
        try {
            return new ResponseEntity<>(cartService.deleteProduct(cartDTO), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"data not deleted");
        }
    }

    @DeleteMapping("/deleteAllProduct")
    public ResponseEntity<Map<String,Object>> deleteAllProduct(@RequestParam("userId") Long userId){
        try {
            return new ResponseEntity<>(cartService.deleteAllProduct(userId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"data not deleted");
        }
    }

//    @PostMapping("/deleteSelectedProduct")
//    public ResponseEntity<Map<String,Object>> deleteSelectedProduct(@RequestBody CartDTO cartDTO){
//        try {
//            return new ResponseEntity<>(cartService.deleteSelectedProduct(cartDTO), HttpStatus.OK);
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"data not deleted");
//        }
//    }
}
