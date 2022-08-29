package com.user.service;

import com.user.DTO.PromocodeDTO;
import com.user.DTO.PromocodeListDTO;

import java.util.Map;

public interface PromocodeService {
    Map<String, Object> save(PromocodeDTO promocodeDTO);

    Map<String, Object> deleteAddress(Long id);

    Map<String, Object> updatePromocode(Long id, PromocodeDTO promocodeDTO);

    Map<String,Object> getAllPromocode(PromocodeListDTO promocodeListDTO);
}
