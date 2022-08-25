package com.category.service;

import com.category.entity.CategoryEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CategoriesService {
    Map<String,Object> save(CategoryEntity categoryEntity);

    Map<String,Object> getCategoryById(Long cId);

    Map<String,Object> uploadCategoryImage(Long cId, MultipartFile multipartFile);

    Map<String,Object> deleteCategoriesById(Long cId);

    Map<String,Object> updateIsactive(Long cId);
}
