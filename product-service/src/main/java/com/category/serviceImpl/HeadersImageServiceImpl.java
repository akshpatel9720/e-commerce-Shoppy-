package com.category.serviceImpl;

import com.category.DTO.HeaderImageListDTO;
import com.category.DTO.HeadersImageDTO;
import com.category.DTO.ResponseDTO;
import com.category.DTO.ResponseMessage;
import com.category.entity.HeadersImageEntity;
import com.category.entity.ProductEntity;
import com.category.repository.HeadersImageRepo;
import com.category.service.AuthenticationService;
import com.category.service.HeadersImageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class HeadersImageServiceImpl implements HeadersImageService {

    @Autowired
    HeadersImageRepo headersImageRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Override
    public Map<String, Object> save(String DTO, MultipartFile multipartFile, String token) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        HeadersImageDTO headersImageDTO = new ObjectMapper().readValue(DTO, HeadersImageDTO.class);
        ResponseDTO authReponse = authenticationService.isAuthenticated(token);
        if (authReponse.getStatus()) {
            HeadersImageEntity headersImageEntity = new HeadersImageEntity();
            if (headersImageDTO != null) {
                //headersImageEntity.setCategoryId(headersImageDTO.getCategoryId());
                headersImageEntity.setName(headersImageDTO.getName());
                headersImageEntity.setDescription(headersImageDTO.getDescription());
                headersImageEntity.setCategoryId(headersImageDTO.getCategoryId());
                headersImageEntity.setCreatedAt(LocalDateTime.now());
                headersImageEntity.setIsActive(Boolean.TRUE);
                if (multipartFile != null) {
                    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", ResponseMessage.CLOUD_NAME,
                            "api_key", ResponseMessage.API_KEY, "api_secret", ResponseMessage.API_SECRET));
                    try {
                        Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.asMap("public_id", "header_Image"));
                        String url = uploadResult.get("url").toString();
                        headersImageEntity.setImgUrl(url);
                        HeadersImageEntity headersEntity = headersImageRepo.save(headersImageEntity);
                        map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
                        map.put(ResponseMessage.RESPONSE_DATA, headersEntity);
                        map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.HEADER_UPLOADED_SUCESSFULLY);
                    } catch (Exception e) {
                        e.printStackTrace();
                        map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
                        map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.HEADER_NOT_UPLOADED);
                        map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
                    }
                } else {
                    map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
                    map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.ENTER_VALID_DATA);
                    map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
                }
            }
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_401);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.AUTHENTICATION_FAIL);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> getHeaderById(Long id, String token) {
        Map<String, Object> map = new HashMap<>();
        ResponseDTO authReponse = authenticationService.isAuthenticated(token);
        if (authReponse.getStatus()) {
            Optional<HeadersImageEntity> existHeader = headersImageRepo.findById(id);
            if (existHeader.isPresent()) {
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_FIND_SUCCESSFULLY);
                map.put(ResponseMessage.RESPONSE_DATA, existHeader);
            } else {
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_NOT_PRESENT);
                map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
            }
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_401);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.AUTHENTICATION_FAIL);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> updateHeaderImg(Long id, MultipartFile multipartFile, String token) {
        Map<String, Object> map = new HashMap<>();
        ResponseDTO authReponse = authenticationService.isAuthenticated(token);
        if (authReponse.getStatus()) {
            Optional<HeadersImageEntity> existHeader = headersImageRepo.findById(id);
            if (existHeader.isPresent()) {
                HeadersImageEntity headersImage = existHeader.get();
                Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", ResponseMessage.CLOUD_NAME,
                        "api_key", ResponseMessage.API_KEY, "api_secret", ResponseMessage.API_SECRET));
                try {
                    Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(),
                            ObjectUtils.asMap("public_id", "header_Image"));
                    String url = uploadResult.get("url").toString();
                    headersImage.setImgUrl(url);
                    headersImage.setUpdatedAt(LocalDateTime.now());
                    HeadersImageEntity headersImage1 = headersImageRepo.save(headersImage);
                    map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
                    map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.HEADER_UPLOADED_SUCESSFULLY);
                    map.put(ResponseMessage.RESPONSE_DATA, headersImage1);
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
                    map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.HEADER_NOT_UPLOADED);
                    map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
                }
            } else {
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.HEADER_IS_NOT_PRESENT);
                map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
            }
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_401);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.AUTHENTICATION_FAIL);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> getHeaderList(HeaderImageListDTO headerImageListDTO, String token) {
        Map<String, Object> map = new HashMap<>();
        ResponseDTO authReponse = authenticationService.isAuthenticated(token);
        if (authReponse.getStatus()) {
            Boolean status = Boolean.valueOf(headerImageListDTO.getWhere().get("isActive").toString());
            Integer page = Integer.valueOf(headerImageListDTO.getPagination().get("page").toString());
            Integer rowPerPage = Integer.valueOf(headerImageListDTO.getPagination().get("rowsPerPage").toString());
            Page<HeadersImageEntity> headersImageEntities = null;
            Pageable pageable = PageRequest.of(page, rowPerPage);
            List<HeadersImageEntity> headersImageEntityArrayList = new ArrayList<>();
            headersImageEntities = headersImageRepo.findStatusAndUserId(status, pageable);
            for (HeadersImageEntity headersImage : headersImageEntities) {
                headersImageEntityArrayList.add(headersImage);
            }
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_FIND_SUCCESSFULLY);
            map.put(ResponseMessage.RESPONSE_DATA, headersImageEntityArrayList);
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_401);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.AUTHENTICATION_FAIL);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }
}
