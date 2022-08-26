package com.category.serviceImpl;

import com.category.DTO.CartDTO;
import com.category.DTO.CategoryDTO;
import com.category.entity.CartEntity;
import com.category.entity.CategoryEntity;
import com.category.repository.CartRepo;
import com.category.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

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
            for (String pId:cartDTO.getPId()) {
                addToCart.setPId(String.join(",",pId));
            }
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
}
