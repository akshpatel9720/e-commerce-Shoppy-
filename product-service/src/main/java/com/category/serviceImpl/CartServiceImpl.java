package com.category.serviceImpl;

import com.category.DTO.CartDTO;
import com.category.DTO.ResponseMessage;
import com.category.entity.CartEntity;

import com.category.repository.CartRepo;
import com.category.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
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
            addToCart.setIsActive(Boolean.TRUE);
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
    public Map<String, Object> deleteProduct(CartDTO cartDTO) {
        Map<String, Object> map = new HashMap<>();
        CartEntity deleteProduct = new CartEntity();
        if (cartDTO != null) {
            Optional<CartEntity> cartEntity = cartRepo.deleteProduct(cartDTO.getPId());
            CartEntity cart = cartEntity.get();
            if (cartEntity.isPresent()) {
                cart.setUserId(cartDTO.getUserId());
                for (String pid : cartDTO.getPId()) {
                    cart.setPId(pid);
                }
                cartRepo.delete(cart);
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
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.ENTER_VALID_DATA);
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

//    @Override
//    public Map<String, Object> deleteSelectedProduct(CartDTO cartDTO) {
//        CartEntity cartEntity = new CartEntity();
//        if (cartDTO != null) {
//            cartEntity.setUserId(cartEntity.getUserId());
//            for (String pid: cartDTO.getPId()) {
//                cartEntity.setPId(pid);
//            }
//
//
//        }
//        return null;
//    }


}
