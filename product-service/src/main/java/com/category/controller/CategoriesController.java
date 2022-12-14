package com.category.controller;

import com.category.DTO.CartListDTO;
import com.category.DTO.CategoryDTO;
import com.category.DTO.CategoryListDTO;
import com.category.entity.CategoryEntity;
import com.category.exception.ProductException;
import com.category.service.CategoriesService;
import org.omg.CORBA.UserException;
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
    public ResponseEntity<Map<String, Object>> save(@RequestBody CategoryDTO categoryDTO, @RequestHeader("Authorization") String token) {
        try {
            return new ResponseEntity<>(categoriesService.save(categoryDTO,token), HttpStatus.OK);
        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            throw new ProductException.HandleException("");
        }
    }

    @GetMapping("/getCategoryById")
    public ResponseEntity<Map<String, Object>> getCategoryById(@RequestParam("cId") Long cId, @RequestHeader("Authorization") String token) {
        try {
            return new ResponseEntity<>(categoriesService.getCategoryById(cId,token), HttpStatus.OK);
        } catch (Exception e) {
            throw new ProductException.HandleException("category is not find");
        }
    }

    @PostMapping("/uploadCategoryImage")
    public ResponseEntity<Map<String, Object>> uploadProfile(@RequestParam("cId") Long cId,
                                                             @RequestParam("categoryImg") MultipartFile multipartFile, @RequestHeader("Authorization") String token) {
        try {
            return new ResponseEntity<>(categoriesService.uploadCategoryImage(cId, multipartFile,token), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/deleteCategoriesById")
    public ResponseEntity<Map<String,Object>> deleteCategoriesById(@RequestParam("cId") Long cId, @RequestHeader("Authorization") String token){
        try {
            return new ResponseEntity<>(categoriesService.deleteCategoriesById(cId,token), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String,Object>> updateIsactive(@RequestParam("cId") Long cId,@RequestBody CategoryDTO categoryDTO, @RequestHeader("Authorization") String token){
        try {
            return new ResponseEntity<>(categoriesService.update(cId,categoryDTO,token), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/searchCategory")
    public ResponseEntity<Map<String, Object>> search(@RequestParam("Text") String Text, @RequestHeader("Authorization") String token) {
        try {
            return new ResponseEntity<>(categoriesService.search(Text,token), HttpStatus.OK);
        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/getCategoryList")
    public ResponseEntity<Map<String, Object>> getUser(@RequestBody CategoryListDTO categoryListDTO, @RequestHeader("Authorization") String token) {
        try {
            return new ResponseEntity<>(categoriesService.getUser(categoryListDTO,token), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"data not fetched");
        }
    }


}
