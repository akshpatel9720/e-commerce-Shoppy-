package com.user.service;

import com.user.DTO.RedeemCodeDTO;
import com.user.DTO.RedeemCodeListDTO;

import java.util.Map;

public interface RedeemCodeService {
    Map<String, Object> addUserRedeemCode(RedeemCodeDTO redeemCodeDTO);

    Map<String, Object> updatePromocode(Long id, RedeemCodeDTO redeemCodeDTO);

    Map<String, Object> getAllRedeemCode(RedeemCodeListDTO redeemCodeListDTO);
}
