package com.category.serviceImpl;

import com.category.DTO.CartDTO;
import com.category.DTO.CartListDTO;
import com.category.DTO.ResponseMessage;
import com.category.entity.CartEntity;

import com.category.repository.CartRepo;
import com.category.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CartServiceImpl implements CartService {

    private static final Object VALID_DATA = "data is not present ";
    @Autowired
    CartRepo cartRepo;

    @Override
    public Map<String, Object> save(CartDTO cartDTO) {
        Map<String, Object> map = new HashMap<>();
        CartEntity addToCart = new CartEntity();
        if (cartDTO != null) {
            addToCart.setUserId(cartDTO.getUserId());
            addToCart.setCreatedAt(LocalDateTime.now());
            for (String pId : cartDTO.getPId()) {
                addToCart.setPId(String.join(",", pId));
            }
            addToCart.setProductQuantity(cartDTO.getProductQuantity());
            cartRepo.save(addToCart);
            map.put("status", "200");
            map.put("message", "data saved successfully");
            map.put("data", addToCart);
        } else {
            map.put("status", "400");
            map.put("message", "data not save");
            map.put("data", new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> deleteAProduct(CartDTO cartDTO) {
        Map<String, Object> map = new HashMap<>();
        if (cartDTO != null) {
            Optional<CartEntity> cartEntity = cartRepo.findProduct(cartDTO.getUserId(), cartDTO.getPId());
            CartEntity deleteProductByPId = cartEntity.get();
            if (cartEntity != null) {
                cartRepo.delete(deleteProductByPId);
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_DELETE_SUCCESSFULLY);
                map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
            } else {
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_NOT_DELETED);
                map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
            }
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_NOT_PRESENT);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> deleteAllProduct(Long userId) {
        Map<String, Object> map = new HashMap<>();
        Optional<CartEntity> cartEntity = cartRepo.findById(userId);
        if (cartEntity.isPresent()) {
            cartRepo.deleteById(userId);
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_DELETE_SUCCESSFULLY);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_NOT_PRESENT);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> deleteSelectedProduct(CartDTO cartDTO) {
        Map<String, Object> map = new HashMap<>();
        if (cartDTO != null) {
            Optional<CartEntity> cartEntity = cartRepo.findProduct(cartDTO.getUserId(), cartDTO.getPId());
            CartEntity deleteProductByPId = cartEntity.get();
            if (cartEntity != null) {
                cartRepo.delete(deleteProductByPId);
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_DELETE_SUCCESSFULLY);
                map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
            } else {
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_NOT_DELETED);
                map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
            }
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_NOT_PRESENT);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> getUser(CartListDTO cartListDTO) {
        Map<String, Object> map = new HashMap<>();
        Boolean status = Boolean.valueOf(cartListDTO.getWhere().get("isActive").toString());
        Long userId = Long.valueOf(cartListDTO.getWhere().get("userId").toString());
        Integer page = Integer.valueOf(cartListDTO.getPagination().get("page").toString());
        Integer rowPerPage = Integer.valueOf(cartListDTO.getPagination().get("rowsPerPage").toString());
        Page<CartEntity> cartEntities = null;
        Pageable pageable = PageRequest.of(page,rowPerPage);
        List<CartEntity> cartEntityList = new ArrayList<>();
        cartEntities=cartRepo.findStatusAndUserId(status,userId,pageable);
        for (CartEntity cartEntites:cartEntities) {
                cartEntityList.add(cartEntites);
        }
        map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
        map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_FIND_SUCCESSFULLY);
        map.put(ResponseMessage.RESPONSE_DATA, cartEntityList);
        return map;
    }


}
