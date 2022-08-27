package com.category.serviceImpl;

import com.category.DTO.CartListDTO;
import com.category.DTO.CategoryDTO;
import com.category.DTO.CategoryListDTO;
import com.category.DTO.ResponseMessage;
import com.category.entity.CartEntity;
import com.category.entity.CategoryEntity;
import com.category.repository.CategoriesRepo;
import com.category.service.CategoriesService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    CategoriesRepo categoriesRepo;

    @Override
    public Map<String, Object> save(CategoryDTO categoryDTO) {
        Map<String, Object> map = new HashMap<>();
        CategoryEntity saveNewUser = new CategoryEntity();
        if (categoryDTO != null) {
            saveNewUser.setCategoryName(categoryDTO.getCategoryName());
            saveNewUser.setIsActive(Boolean.TRUE);
            saveNewUser.setCreatedAt(LocalDateTime.now());
            categoriesRepo.save(saveNewUser);
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
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", ResponseMessage.CLOUD_NAME,
                "api_key", ResponseMessage.API_KEY, "api_secret", ResponseMessage.API_SECRET));
        System.out.println("image sent successfully");
        try {
            Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(),
                    ObjectUtils.asMap("public_id", "category" + cId));
            String url = uploadResult.get("url").toString();
            categoryImg.setCategoryImg(url);
            categoryImg.setUpdatedAt(LocalDateTime.now());
            categoriesRepo.save(categoryImg);
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
            map.put(ResponseMessage.RESPONSE_DATA, url);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.PROFILE_UPLOADED_SUCESSFULLY);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.SOMETING_WENT_WRONG);
        }
        return map;
    }

    @Override
    public Map<String, Object> deleteCategoriesById(Long cId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Optional<CategoryEntity> existCategory = categoriesRepo.findById(cId);
        if (existCategory.isPresent()) {
            existCategory.get().setIsActive(Boolean.FALSE);
            categoriesRepo.deleteById(cId);
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_DELETE_SUCCESSFULLY);
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_NOT_DELETED);
        }
        return map;
    }

    @Override
    public Map<String, Object> updateIsactive(Long cId) {
        Map<String, Object> map = new HashMap<>();
        Optional<CategoryEntity> existCategoryIsActive = categoriesRepo.findById(cId);
        if (existCategoryIsActive.isPresent()) {
            CategoryEntity categoryEntity = existCategoryIsActive.get();
            if (categoryEntity.getIsActive().equals(Boolean.TRUE)) {
                categoryEntity.setIsActive(Boolean.FALSE);
                categoryEntity.setUpdatedAt(LocalDateTime.now());
            } else {
                categoryEntity.setIsActive(Boolean.TRUE);
                categoryEntity.setUpdatedAt(LocalDateTime.now());
            }
            categoriesRepo.save(categoryEntity);
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.ISACTIVE_UPDATE_SUCCESS);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.ISACTIVE_NOT_UPDATE);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> search(String Text) {
        Map<String, Object> map = new HashMap<>();
        if (!Text.isEmpty()) {
            List<CategoryEntity> searchdata = categoriesRepo.search(Text);
            if (!searchdata.isEmpty()) {
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.SUCCESS_SEARCH);
                map.put(ResponseMessage.RESPONSE_DATA, searchdata);
            } else {
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.FAIL_SEARCH);
                map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
            }
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.FAIL_SEARCH_TEXT_NOT);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> getUser(CategoryListDTO categoryListDTO) {
        Map<String, Object> map = new HashMap<>();
        Boolean status = Boolean.valueOf(categoryListDTO.getWhere().get("isActive").toString());
        Integer page = Integer.valueOf(categoryListDTO.getPagination().get("page").toString());
        Integer rowPerPage = Integer.valueOf(categoryListDTO.getPagination().get("rowsPerPage").toString());
        Page<CategoryEntity> categoryEntities = null;
        Pageable pageable = PageRequest.of(page, rowPerPage);
        List<CategoryEntity> cartEntityList = new ArrayList<>();
        categoryEntities = categoriesRepo.findStatusAndUserId(status, pageable);
        for (CategoryEntity categoryEntity : categoryEntities) {
            cartEntityList.add(categoryEntity);
        }
        map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
        map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_FIND_SUCCESSFULLY);
        map.put(ResponseMessage.RESPONSE_DATA, cartEntityList);
        return map;
    }
}
