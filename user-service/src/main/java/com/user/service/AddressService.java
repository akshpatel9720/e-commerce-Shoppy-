package com.user.service;

import com.user.DTO.AddressDTO;
import com.user.DTO.AddressListDTO;

import java.util.Map;

public interface AddressService {
    Map<String, Object> save(AddressDTO addressDTO);

    Map<String, Object> deleteAddress(Long userId, AddressDTO addressDTO);

    Map<String, Object> getAddress(AddressListDTO addressListDTO);

    Map<String, Object> updateAddress(Long id, AddressDTO addressDTO);
}
