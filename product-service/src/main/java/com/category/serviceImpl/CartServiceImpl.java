package com.category.serviceImpl;

import com.category.DTO.CartDTO;
import com.category.DTO.CartListDTO;
import com.category.DTO.ResponseDTO;
import com.category.DTO.ResponseMessage;
import com.category.entity.CartEntity;

import com.category.repository.CartRepo;
import com.category.service.AuthenticationService;
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

    @Autowired
    AuthenticationService authenticationService;

    @Override
    public Map<String, Object> save(CartDTO cartDTO, String token) {
        Map<String, Object> map = new HashMap<>();
        CartEntity addToCart = new CartEntity();
        ResponseDTO authReponse = authenticationService.isAuthenticated(token);
        if (authReponse.getStatus()) {
            if (cartDTO != null) {
                addToCart.setUserId(cartDTO.getUserId());
                addToCart.setCreatedAt(LocalDateTime.now());
                addToCart.setIsActive(Boolean.TRUE);
                addToCart.setPId(String.join(",", cartDTO.getPId()));
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
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_401);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.AUTHENTICATION_FAIL);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> deleteAProduct(Long userId, String pId, String token) {
        Map<String, Object> map = new HashMap<>();
        ResponseDTO authReponse = authenticationService.isAuthenticated(token);
        if (authReponse.getStatus()) {
            if (userId != null && pId != null) {
                Optional<CartEntity> cartEntity = cartRepo.findByuserIdandpId(userId, pId);
                if (cartEntity.isPresent()) {
//                    CartEntity deleteProductByPId = cartEntity.get();
                    cartRepo.delete(cartEntity.get());
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
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_401);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.AUTHENTICATION_FAIL);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> deleteAllProduct(Long userId, String token) {
        Map<String, Object> map = new HashMap<>();
        ResponseDTO authReponse = authenticationService.isAuthenticated(token);
        if (authReponse.getStatus()) {
            List<CartEntity> cartEntity = cartRepo.findByUserId(userId);
            if (cartEntity.size() > 0) {
                cartRepo.deleteByUserId(userId);
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_DELETE_SUCCESSFULLY);
                map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
            } else {
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_NOT_PRESENT);
                map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
            }
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_401);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.AUTHENTICATION_FAIL);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> deleteSelectedProduct(Long userId, String pId, String token) {
        Map<String, Object> map = new HashMap<>();
        ResponseDTO authReponse = authenticationService.isAuthenticated(token);
        if (authReponse.getStatus()) {
            if (userId != null && pId != null) {
                Optional<CartEntity> cartEntity = cartRepo.findByuserIdandpId(userId, pId);
//                CartEntity deleteProductByPId = cartEntity.get();
                if (cartEntity.isPresent()) {
                    cartRepo.delete(cartEntity.get());
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
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_401);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.AUTHENTICATION_FAIL);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> getUser(CartListDTO cartListDTO, String token) {
        Map<String, Object> map = new HashMap<>();
        ResponseDTO authReponse = authenticationService.isAuthenticated(token);
        if (authReponse.getStatus()) {
            Boolean status = Boolean.valueOf(cartListDTO.getWhere().get("isActive").toString());
            Long userId = Long.valueOf(cartListDTO.getWhere().get("userId").toString());
            Integer page = Integer.valueOf(cartListDTO.getPagination().get("page").toString());
            Integer rowPerPage = Integer.valueOf(cartListDTO.getPagination().get("rowsPerPage").toString());
            Page<CartEntity> cartEntities = null;
            Pageable pageable = PageRequest.of(page, rowPerPage);
            List<CartEntity> cartEntityList = new ArrayList<>();
            cartEntities = cartRepo.findStatusAndUserId(status, userId, pageable);
            for (CartEntity cartEntites : cartEntities) {
                cartEntityList.add(cartEntites);
            }
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_FIND_SUCCESSFULLY);
            map.put(ResponseMessage.RESPONSE_DATA, cartEntityList);
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_401);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.AUTHENTICATION_FAIL);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }

}
