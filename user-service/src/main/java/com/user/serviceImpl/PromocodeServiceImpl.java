package com.user.serviceImpl;

import com.user.DTO.PromocodeDTO;
import com.user.DTO.PromocodeListDTO;
import com.user.entity.PromocodeEntity;
import com.user.enums.PromocodeEnum;
import com.user.repository.PromocodeRepository;
import com.user.service.PromocodeService;
import com.user.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PromocodeServiceImpl implements PromocodeService {

    @Autowired
    PromocodeRepository promocodeRepository;


    @Override
    public Map<String, Object> save(PromocodeDTO promocodeDTO) {
        Map<String, Object> map = new HashMap<>();
        PromocodeEntity savePromocode = new PromocodeEntity();
        if (promocodeDTO != null) {
            if (promocodeDTO.getType().equalsIgnoreCase(PromocodeEnum.FLAT.toString())) {
                savePromocode.setCouponcode(promocodeDTO.getCouponcode());
                savePromocode.setDescription(promocodeDTO.getDescription());
                savePromocode.setType("FLAT");
                savePromocode.setMinvalue(promocodeDTO.getMinvalue());
                savePromocode.setMaxdiscountvalue(promocodeDTO.getMaxdiscountvalue());
                savePromocode.setIsActive(Boolean.TRUE);
                savePromocode.setCreatedAt(LocalDateTime.now());
                map.put(ResponseMessage.STATUS, ResponseMessage.SUCCESS_API_CODE);
                map.put(ResponseMessage.MESSAGE, ResponseMessage.PROMO_CODE_SAVE);
                map.put(ResponseMessage.DATA, savePromocode);
            } else {
                savePromocode.setCouponcode(promocodeDTO.getCouponcode());
                savePromocode.setDescription(promocodeDTO.getDescription());
                savePromocode.setType("PERCENTAGE");
                savePromocode.setMinvalue(promocodeDTO.getMinvalue());
                savePromocode.setMaxdiscountvalue(promocodeDTO.getMaxdiscountvalue());
                savePromocode.setIsActive(Boolean.TRUE);
                savePromocode.setCreatedAt(LocalDateTime.now());
                map.put(ResponseMessage.STATUS, ResponseMessage.SUCCESS_API_CODE);
                map.put(ResponseMessage.MESSAGE, ResponseMessage.PROMO_CODE_SAVE);
                map.put(ResponseMessage.DATA, savePromocode);
            }
        } else {
            map.put(ResponseMessage.STATUS, ResponseMessage.FAIL_API_CODE);
            map.put(ResponseMessage.MESSAGE, ResponseMessage.ENTER_DATA);
            map.put(ResponseMessage.DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> deleteAddress(Long id) {
        Map<String, Object> map = new HashMap<>();
        Optional<PromocodeEntity> promocodeEntity = promocodeRepository.findById(id);
        if (promocodeEntity.isPresent()) {
            PromocodeEntity savedPromocode = promocodeEntity.get();
            promocodeRepository.delete(savedPromocode);
            map.put(ResponseMessage.STATUS, ResponseMessage.SUCCESS_API_CODE);
            map.put(ResponseMessage.MESSAGE, ResponseMessage.SUCCESS_MESSAGE_DELETE);
            map.put(ResponseMessage.DATA, new ArrayList<>());
        } else {
            map.put(ResponseMessage.STATUS, ResponseMessage.SUCCESS_API_CODE);
            map.put(ResponseMessage.MESSAGE, ResponseMessage.FAIL_DELETE);
            map.put(ResponseMessage.DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> updatePromocode(Long id, PromocodeDTO promocodeDTO) {
        Map<String, Object> map = new HashMap<>();
        Optional<PromocodeEntity> savedPromocode = promocodeRepository.findById(id);
        if (savedPromocode.isPresent()) {
            if (promocodeDTO.getType().equalsIgnoreCase(PromocodeEnum.FLAT.toString())) {
                savedPromocode.get().setUpdatedAt(LocalDateTime.now());
                savedPromocode.get().setIsActive(promocodeDTO.getIsActive());
                savedPromocode.get().setCouponcode(promocodeDTO.getCouponcode());
                savedPromocode.get().setType("FLAT");
                savedPromocode.get().setDescription(promocodeDTO.getDescription());
                savedPromocode.get().setMinvalue(promocodeDTO.getMinvalue());
                savedPromocode.get().setMaxdiscountvalue(promocodeDTO.getMaxdiscountvalue());
                map.put(ResponseMessage.STATUS, ResponseMessage.SUCCESS_API_CODE);
                map.put(ResponseMessage.MESSAGE, ResponseMessage.PROMO_CODE_UPDATE);
                map.put(ResponseMessage.DATA, savedPromocode);
            } else {
                savedPromocode.get().setUpdatedAt(LocalDateTime.now());
                savedPromocode.get().setIsActive(promocodeDTO.getIsActive());
                savedPromocode.get().setCouponcode(promocodeDTO.getCouponcode());
                savedPromocode.get().setType("PERCENTAGE");
                savedPromocode.get().setDescription(promocodeDTO.getDescription());
                savedPromocode.get().setMinvalue(promocodeDTO.getMinvalue());
                savedPromocode.get().setMaxdiscountvalue(promocodeDTO.getMaxdiscountvalue());
                map.put(ResponseMessage.STATUS, ResponseMessage.SUCCESS_API_CODE);
                map.put(ResponseMessage.MESSAGE, ResponseMessage.PROMO_CODE_UPDATE);
                map.put(ResponseMessage.DATA, savedPromocode);
            }
        } else {
            map.put(ResponseMessage.STATUS, ResponseMessage.FAIL_API_CODE);
            map.put(ResponseMessage.MESSAGE, ResponseMessage.ENTER_VALID_DATA);
            map.put(ResponseMessage.DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> getAllPromocode(PromocodeListDTO promocodeListDTO) {
        Map<String, Object> map = new HashMap<>();
        Boolean status = Boolean.valueOf(promocodeListDTO.getWhere().get("isActive").toString());
        Integer page = Integer.valueOf(promocodeListDTO.getPagination().get("page").toString());
        Integer rowsPerPage = Integer.valueOf(promocodeListDTO.getPagination().get("rowsPerPage").toString());
        Page<PromocodeEntity> promocodeEntityPage = null;
        Pageable pageable = PageRequest.of(page, rowsPerPage);
        promocodeEntityPage = promocodeRepository.findStatus(status, pageable);
        List<PromocodeEntity> promocodeEntitiesList = new ArrayList<>();
        for (PromocodeEntity promocode : promocodeEntityPage) {
            promocodeEntitiesList.add(promocode);
        }
        map.put(ResponseMessage.STATUS, ResponseMessage.SUCCESS_API_CODE);
        map.put(ResponseMessage.MESSAGE, ResponseMessage.DATA_FIND_SUCCESSFULLY);
        map.put(ResponseMessage.DATA, promocodeEntitiesList);
        return map;
    }




}
