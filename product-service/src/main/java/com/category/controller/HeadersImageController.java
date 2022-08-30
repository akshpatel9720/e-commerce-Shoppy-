package com.category.controller;

import com.category.DTO.HeadersImageDTO;
import com.category.DTO.ProductDTO;
import com.category.exception.ProductException;
import com.category.service.HeadersImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/headerImage")
public class HeadersImageController {
    @Autowired
    HeadersImageService headersImageService;

    @PostMapping("/saveHeaderImg")
    public ResponseEntity<Map<String, Object>> save(@RequestBody HeadersImageDTO headersImageDTO, MultipartFile multipartFile, @RequestHeader("Authorization") String token) {
        try {
            return new ResponseEntity<>(headersImageService.save(headersImageDTO, multipartFile, token), HttpStatus.OK);
        } catch (Exception e) {
            throw new ProductException.HandleException("data is not save");
        }
    }

    @GetMapping("/getHeaderById")
    public ResponseEntity<Map<String, Object>> getHeaderById(@RequestParam("id") Long id, @RequestHeader("Authorization") String token) {
        try {
            return new ResponseEntity<>(headersImageService.getHeaderById(id, token), HttpStatus.OK);
        } catch (Exception e) {
            throw new ProductException.HandleException("data is not save");
        }
    }

    @PatchMapping("/updateHeaderImg")
    public ResponseEntity<Map<String, Object>> updateHeaderImg(@RequestParam("id") Long id, MultipartFile multipartFile, @RequestHeader("Authorization") String token) {
        try {
            return new ResponseEntity<>(headersImageService.updateHeaderImg(id, multipartFile, token), HttpStatus.OK);
        } catch (Exception e) {
            throw new ProductException.HandleException("data is not save");
        }
    }
}
