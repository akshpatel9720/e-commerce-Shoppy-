package com.user.serviceImpl;

import com.user.DTO.AddressDTO;
import com.user.DTO.AddressListDTO;
import com.user.entity.AddressEntity;
import com.user.repository.AddressRepository;
import com.user.service.AddressService;
import com.user.util.ResponseMessage;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.rsocket.context.RSocketPortInfoApplicationContextInitializer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Override
    public Map<String, Object> save(AddressDTO addressDTO) {
        Map<String, Object> map = new HashMap<>();
        AddressEntity addressEntity = new AddressEntity();
        if (addressDTO != null) {
            addressEntity.setCreatedAt(LocalDateTime.now());
            addressEntity.setUserId(addressEntity.getUserId());
            addressEntity.setLabel(addressDTO.getLabel());
            addressEntity.setAddress_1(addressDTO.getAddress_1());
            addressEntity.setAddress_2(addressDTO.getAddress_2());
            addressEntity.setPincode(addressDTO.getPincode());
            addressEntity.setLandmark(addressDTO.getLandmark());
            addressEntity.setType(addressDTO.getAddress_1());
            addressEntity.setUpdatedAt(null);
            addressRepository.save(addressEntity);
            map.put(ResponseMessage.STATUS, ResponseMessage.SUCCESS_API_CODE);
            map.put(ResponseMessage.MESSAGE, ResponseMessage.DATA_SAVED_SUCCESSFULLY);
            map.put(ResponseMessage.DATA, addressEntity);
        } else {
            map.put(ResponseMessage.STATUS, ResponseMessage.FAIL_API_CODE);
            map.put(ResponseMessage.MESSAGE, ResponseMessage.DATA_NOT_SAVED);
            map.put(ResponseMessage.DATA, new ArrayList<>());

        }
        return map;
    }

    @Override
    public Map<String, Object> deleteAddress(Long userId, AddressDTO addressDTO) {
        Map<String, Object> map = new HashMap<>();
        Optional<AddressEntity> addressEntity = addressRepository.findById(userId);
        AddressEntity addressEntity1 = addressEntity.get();
        if (addressEntity.isPresent()) {
            addressRepository.delete(addressEntity1);
            map.put(ResponseMessage.STATUS, ResponseMessage.SUCCESS_API_CODE);
            map.put(ResponseMessage.MESSAGE, ResponseMessage.SUCCESS_MESSAGE_DELETE);
            map.put(ResponseMessage.DATA, new ArrayList<>());
        } else {
            map.put(ResponseMessage.STATUS, ResponseMessage.FAIL_API_CODE);
            map.put(ResponseMessage.MESSAGE, ResponseMessage.FAIL_DELETE);
            map.put(ResponseMessage.DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> getAddress(AddressListDTO addressListDTO) {
        Map<String, Object> map = new HashMap<>();
        Boolean status = Boolean.valueOf(addressListDTO.getWhere().get("isActive").toString());
        Integer page = Integer.valueOf(addressListDTO.getPagination().get("page").toString());
        Integer roePerPage = Integer.valueOf(addressListDTO.getPagination().get("rowsPerPage").toString());
        Page<AddressEntity> addressEntities = null;
        Pageable pageable = PageRequest.of(page, roePerPage);
        List<AddressEntity> addressEntityList = new ArrayList<>();
        addressEntities = addressRepository.findStatus(status, pageable);
        for (AddressEntity address : addressEntities) {
            addressEntityList.add(address);
        }
        map.put(ResponseMessage.STATUS, ResponseMessage.SUCCESS_API_CODE);
        map.put(ResponseMessage.MESSAGE, ResponseMessage.DATA_FIND_SUCCESSFULLY);
        map.put(ResponseMessage.DATA, addressEntityList);
        return map;
    }

    @Override
    public Map<String, Object> updateAddress(Long id, AddressDTO addressDTO) {
        Map<String, Object> map = new HashMap<>();
        Optional<AddressEntity> addressEntity = addressRepository.findById(addressDTO.getId());
        if (addressEntity.isPresent()) {
            addressEntity.get().setUpdatedAt(LocalDateTime.now());
            addressEntity.get().setAddress_1(addressDTO.getAddress_1());
            addressEntity.get().setAddress_2(addressDTO.getAddress_2());
            addressEntity.get().setType(addressDTO.getType());
            addressEntity.get().setLandmark(addressDTO.getLandmark());
            addressEntity.get().setPincode(addressDTO.getPincode());
            addressEntity.get().setUserId(addressDTO.getUserId());
            addressEntity.get().setLabel(addressDTO.getLabel());
            AddressEntity updateAddress = addressRepository.save(addressEntity.get());
            map.put(ResponseMessage.STATUS, ResponseMessage.SUCCESS_API_CODE);
            map.put(ResponseMessage.MESSAGE, ResponseMessage.UPDATE_ADDRESS_SUCCESSFULLY);
            map.put(ResponseMessage.DATA, updateAddress);
        } else {
            map.put(ResponseMessage.STATUS, ResponseMessage.FAIL_API_CODE);
            map.put(ResponseMessage.MESSAGE, ResponseMessage.ADDRESS_NOT_UPDATED);
            map.put(ResponseMessage.DATA, new ArrayList<>());
        }
        return map;
    }
}
