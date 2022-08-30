package com.user.service;

import com.user.DTO.PromocodeDTO;
import com.user.DTO.PromocodeListDTO;

import java.util.Map;

public interface PromocodeService {
    Map<String, Object> save(PromocodeDTO promocodeDTO, String authToken);

    Map<String, Object> deleteAddress(Long id, String authToken);

    Map<String, Object> updatePromocode(Long id, PromocodeDTO promocodeDTO, String authToken);

    Map<String, Object> getAllPromocode(PromocodeListDTO promocodeListDTO, String authToken);
}
