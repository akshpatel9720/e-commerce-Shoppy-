package com.user.serviceImpl;

import com.user.DTO.RedeemCodeDTO;
import com.user.DTO.RedeemCodeListDTO;
import com.user.entity.RedeemCodeEntity;
import com.user.repository.RedeemCodeRepository;
import com.user.service.RedeemCodeService;
import com.user.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RedeemCodeServiceImpl implements RedeemCodeService {

    @Autowired
    RedeemCodeRepository redeemCodeRepository;


    @Override
    public Map<String, Object> addUserRedeemCode(RedeemCodeDTO redeemCodeDTO) {
        Map<String, Object> map = new HashMap<>();
        RedeemCodeEntity redeemCodeEntity = new RedeemCodeEntity();
        if (redeemCodeDTO != null) {
            redeemCodeEntity.setPromocodeId(redeemCodeEntity.getPromocodeId());
            redeemCodeEntity.setUserId(redeemCodeEntity.getUserId());
            redeemCodeEntity.setCreatedAt(LocalDateTime.now());
            redeemCodeEntity.setIsActive(Boolean.TRUE);
            map.put(ResponseMessage.STATUS, ResponseMessage.SUCCESS_API_CODE);
            map.put(ResponseMessage.MESSAGE, ResponseMessage.REDEEM_CODE_SUCESS);
            map.put(ResponseMessage.DATA, redeemCodeEntity);
        } else {
            map.put(ResponseMessage.STATUS, ResponseMessage.FAIL_API_CODE);
            map.put(ResponseMessage.MESSAGE, ResponseMessage.REDEEM_CODE_NOT_SUCESS);
            map.put(ResponseMessage.DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> updatePromocode(Long id, RedeemCodeDTO redeemCodeDTO) {
        Map<String, Object> map = new HashMap<>();
        Optional<RedeemCodeEntity> savedRedeemCode = redeemCodeRepository.findById(id);
        if (savedRedeemCode.isPresent()) {
            savedRedeemCode.get().setUpdatedAt(LocalDateTime.now());
            savedRedeemCode.get().setPromocodeId(redeemCodeDTO.getPromocodeId());
            savedRedeemCode.get().setUserId(redeemCodeDTO.getUserId());
            map.put(ResponseMessage.STATUS, ResponseMessage.SUCCESS_API_CODE);
            map.put(ResponseMessage.MESSAGE, ResponseMessage.REDEEM_CODE_UPDATE_SUCESS);
            map.put(ResponseMessage.DATA, savedRedeemCode);
        } else {
            map.put(ResponseMessage.STATUS, ResponseMessage.FAIL_API_CODE);
            map.put(ResponseMessage.MESSAGE, ResponseMessage.REDEEM_CODE_NOTUPDATE_SUCESS);
            map.put(ResponseMessage.DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> getAllRedeemCode(RedeemCodeListDTO redeemCodeListDTO) {
        Map<String, Object> map = new HashMap<>();
        Boolean status = Boolean.valueOf(redeemCodeListDTO.getWhere().get("isActive").toString());
        Integer page = Integer.valueOf(redeemCodeListDTO.getPagination().get("page").toString());
        Integer rowsPerPage = Integer.valueOf(redeemCodeListDTO.getPagination().get("rowsPerPage").toString());
        Page<RedeemCodeEntity> redeemCodeEntityPage = null;
        Pageable pageable = PageRequest.of(page, rowsPerPage);
        redeemCodeEntityPage = redeemCodeRepository.findStatus(status, pageable);
        List<RedeemCodeEntity> redeemCodeEntityList = new ArrayList<>();
        for (RedeemCodeEntity redeemcode : redeemCodeEntityPage) {
            redeemCodeEntityList.add(redeemcode);
        }
        map.put(ResponseMessage.STATUS, ResponseMessage.SUCCESS_API_CODE);
        map.put(ResponseMessage.MESSAGE, ResponseMessage.DATA_FIND_SUCCESSFULLY);
        map.put(ResponseMessage.DATA, redeemCodeEntityList);
        return map;
    }
}
