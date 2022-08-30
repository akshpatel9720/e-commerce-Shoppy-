package com.category.controller;

import com.category.DTO.CartDTO;
import com.category.DTO.CartListDTO;
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
    public ResponseEntity<Map<String, Object>> save(@RequestBody CartDTO cartDTO, @RequestHeader("Authorization") String token) {
        try {
            return new ResponseEntity<>(cartService.save(cartDTO, token), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "data not saved");
        }
    }

    @DeleteMapping("/deleteAProduct")
    public ResponseEntity<Map<String, Object>> deleteAProduct(@RequestBody CartDTO cartDTO, @RequestHeader("Authorization") String token) {
        try {
            return new ResponseEntity<>(cartService.deleteAProduct(cartDTO, token), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "data not deleted");
        }
    }

    @DeleteMapping("/deleteAllProduct")
    public ResponseEntity<Map<String, Object>> deleteAllProduct(@RequestParam("userId") Long userId, @RequestHeader("Authorization") String token) {
        try {
            return new ResponseEntity<>(cartService.deleteAllProduct(userId, token), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "data not deleted");
        }
    }

    @PostMapping("/deleteSelectedProduct")
    public ResponseEntity<Map<String, Object>> deleteSelectedProduct(@RequestBody CartDTO cartDTO, @RequestHeader("Authorization") String token) {
        try {
            return new ResponseEntity<>(cartService.deleteSelectedProduct(cartDTO, token), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "data not deleted");
        }
    }

    @PostMapping("/getCartList")
    public ResponseEntity<Map<String, Object>> getUser(@RequestBody CartListDTO cartListDTO, @RequestHeader("Authorization") String token) {
        try {
            return new ResponseEntity<>(cartService.getUser(cartListDTO, token), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "data not fetched");
        }
    }
}
