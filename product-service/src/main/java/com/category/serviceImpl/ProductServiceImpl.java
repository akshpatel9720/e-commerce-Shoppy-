package com.category.serviceImpl;

import com.category.DTO.ResponseMessage;
import com.category.entity.ProductEntity;
import com.category.repository.ProductRepo;
import com.category.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepo productRepo;

    @Override
    public Map<String, Object> getProductById(Long pId) {
        Map<String, Object> map = new HashMap<>();
        Optional<ProductEntity> existUser = productRepo.findById(pId);
        if (existUser.isPresent()) {
            map.put(ResponseMessage.RESPONSE_STATUS,ResponseMessage.STATUS_200);
            map.put(ResponseMessage.RESPONSE_MESSAGE,ResponseMessage.DATA_FIND_SUCCESSFULLY);
            map.put(ResponseMessage.RESPONSE_DATA,existUser);
        }else {
            map.put(ResponseMessage.RESPONSE_STATUS,ResponseMessage.STATUS_400);
            map.put(ResponseMessage.RESPONSE_MESSAGE,ResponseMessage.DATA_NOT_PRESENT);
        }
        return map;
    }

    @Override
    public Map<String, Object> save(ProductEntity productEntity) {
        Map<String, Object> map = new HashMap<>();
        ProductEntity saveProduct=new ProductEntity();
        if (productEntity!=null){
            saveProduct.setCreatedAt(productEntity.getCreatedAt());
            saveProduct.setIsActive(Boolean.TRUE);
            saveProduct.setName(productEntity.getName());
            saveProduct.setPrice(productEntity.getPrice());
            saveProduct.setDiscountPrice(productEntity.getDiscountPrice());
            saveProduct.setCategoryId(productEntity.getCategoryId());
            saveProduct.setQuantity(productEntity.getQuantity());
            saveProduct.setSpecification(productEntity.getSpecification());
            saveProduct.setUserId(productEntity.getUserId());
            productRepo.save(saveProduct);
            map.put(ResponseMessage.RESPONSE_STATUS,ResponseMessage.STATUS_200);
            map.put(ResponseMessage.RESPONSE_MESSAGE,ResponseMessage.DATA_SAVED_SUCCESSFULLY);
            map.put(ResponseMessage.RESPONSE_DATA,saveProduct);
        }else {
            map.put(ResponseMessage.RESPONSE_STATUS,ResponseMessage.STATUS_400);
            map.put(ResponseMessage.RESPONSE_MESSAGE,ResponseMessage.DATA_NOT_SAVED);
        }
        return map;
    }


}
