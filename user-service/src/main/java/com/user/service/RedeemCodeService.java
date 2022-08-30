package com.user.service;

import com.user.DTO.RedeemCodeDTO;
import com.user.DTO.RedeemCodeListDTO;

import java.util.Map;

public interface RedeemCodeService {
    Map<String, Object> addUserRedeemCode(RedeemCodeDTO redeemCodeDTO, String authToken);

    Map<String, Object> updatePromocode(Long id, RedeemCodeDTO redeemCodeDTO, String authToken);

    Map<String, Object> getAllRedeemCode(RedeemCodeListDTO redeemCodeListDTO, String authToken);
}
