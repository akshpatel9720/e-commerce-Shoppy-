package com.category.controller;

import com.category.entity.CategoryEntity;
import com.category.exception.ProductException;
import com.category.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/category")
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    @PostMapping("/saveCategory")
    public ResponseEntity<Map<String, Object>> save(@RequestBody CategoryEntity categoryEntity) {
        try {
            return new ResponseEntity<>(categoriesService.save(categoryEntity), HttpStatus.OK);
        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            throw new ProductException.HandleException("");
        }
    }

    @GetMapping("/getCategoryById")
    public ResponseEntity<Map<String, Object>> getCategoryById(@RequestParam("cId") Long cId) {
        try {
            return new ResponseEntity<>(categoriesService.getCategoryById(cId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ProductException.HandleException("category is not find");
        }
    }

    @PostMapping("/uploadCategoryImage")
    public ResponseEntity<Map<String, Object>> uploadProfile(@RequestParam("cId") Long cId,
                                                             @RequestParam("categoryImg") MultipartFile multipartFile) {
        try {
            return new ResponseEntity<>(categoriesService.uploadCategoryImage(cId, multipartFile), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/deleteCategoriesById")
    public ResponseEntity<Map<String,Object>> deleteCategoriesById(@RequestParam("cId") Long cId){
        try {
            return new ResponseEntity<>(categoriesService.deleteCategoriesById(cId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/updateIsactive")
    public ResponseEntity<Map<String,Object>> updateIsactive(@RequestParam("cId") Long cId){
        try {
            return new ResponseEntity<>(categoriesService.updateIsactive(cId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
