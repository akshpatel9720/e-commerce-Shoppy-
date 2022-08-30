package com.user.service;

import com.user.DTO.AddressDTO;
import com.user.DTO.AddressListDTO;

import java.util.Map;

public interface AddressService {
    Map<String, Object> save(AddressDTO addressDTO, String authToken);

    Map<String, Object> deleteAddress(Long userId, String authToken);

    Map<String, Object> getAddress(AddressListDTO addressListDTO, String authToken);

    Map<String, Object> updateAddress(Long id, AddressDTO addressDTO, String authToken);
}
