package com.category.service;

import com.category.DTO.CategoryDTO;
import com.category.DTO.CategoryListDTO;
import com.category.entity.CategoryEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CategoriesService {
    Map<String, Object> save(CategoryDTO categoryDTO, String token);

    Map<String, Object> getCategoryById(Long cId, String token);

    Map<String, Object> uploadCategoryImage(Long cId, MultipartFile multipartFile, String token);

    Map<String, Object> deleteCategoriesById(Long cId, String token);

    Map<String, Object> updateIsactive(Long cId, String token);

    Map<String, Object> search(String Text, String token);


    Map<String, Object> getUser(CategoryListDTO categoryListDTO, String token);
}
