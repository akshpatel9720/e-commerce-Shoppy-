package com.category.serviceImpl;

import com.category.DTO;
import com.category.entity.CategoryEntity;
import com.category.repository.CategoriesRepo;
import com.category.service.CategoriesService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    CategoriesRepo categoriesRepo;

    @Override
    public Map<String, Object> save(CategoryEntity categoryEntity) {
        Map<String, Object> map = new HashMap<>();
        if (categoryEntity != null) {
            categoriesRepo.save(categoryEntity);
            map.put("status", "200");
            map.put("message", "data saved successfully");
            map.put("data", new ArrayList<>());
        } else {
            map.put("status", "400");
            map.put("message", "data not save");
            map.put("data", new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> getCategoryById(Long cId) {
        Map<String, Object> map = new HashMap<>();
        Optional<CategoryEntity> category = categoriesRepo.findById(cId);
        if (category.isPresent()) {
            map.put("STATUS", "200");
            map.put("MESSAGE", "data find successfully");
            map.put("DATA", category);
        } else {
            map.put("STATUS", "400");
            map.put("MESSAGE", "data is not present");
            map.put("DATA", new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> uploadCategoryImage(Long cId, MultipartFile multipartFile) {
        Map<String, Object> map = new HashMap<String, Object>();
        CategoryEntity categoryImg = categoriesRepo.findById(cId).get();
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", DTO.CLOUD_NAME,
                "api_key", DTO.API_KEY, "api_secret", DTO.API_SECRET));
        System.out.println("image sent successfully");
        try {
            Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(),
                    ObjectUtils.asMap("public_id", "category_profile/" + cId));
            String url = uploadResult.get("url").toString();
            categoryImg.setCategoryImg(url);
            categoriesRepo.save(categoryImg);
            map.put(DTO.RESPONSE_STATUS, DTO.STATUS_200);
            map.put(DTO.RESPONSE_DATA, url);
            map.put(DTO.RESPONSE_MESSAGE, DTO.PROFILE_UPLOADED_SUCESSFULLY);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(DTO.RESPONSE_STATUS, DTO.STATUS_400);
            map.put(DTO.RESPONSE_MESSAGE, DTO.SOMETING_WENT_WRONG);
        }
        return map;
    }

    @Override
    public Map<String, Object> deleteCategoriesById(Long cId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Optional<CategoryEntity> existCategory = categoriesRepo.findById(cId);
        if (existCategory.isPresent()){
            categoriesRepo.deleteById(cId);
            map.put(DTO.RESPONSE_STATUS, DTO.STATUS_200);
            map.put(DTO.RESPONSE_MESSAGE, DTO.DATA_DELETE_SUCCESSFULLY);
        }else {
            map.put(DTO.RESPONSE_STATUS, DTO.STATUS_400);
            map.put(DTO.RESPONSE_MESSAGE, DTO.DATA_NOT_DELETED);
        }
        return map;
    }
}
