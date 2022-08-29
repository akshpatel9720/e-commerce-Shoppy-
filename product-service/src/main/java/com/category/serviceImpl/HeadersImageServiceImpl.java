package com.category.serviceImpl;

import com.category.DTO.HeadersImageDTO;
import com.category.DTO.ResponseMessage;
import com.category.entity.HeadersImageEntity;
import com.category.repository.HeadersImageRepo;
import com.category.service.HeadersImageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class HeadersImageServiceImpl implements HeadersImageService {

    @Autowired
    HeadersImageRepo headersImageRepo;


    @Override
    public Map<String, Object> save(HeadersImageDTO headersImageDTO, MultipartFile multipartFile) {
        Map<String, Object> map = new HashMap<>();
        HeadersImageEntity headersImageEntity = new HeadersImageEntity();
        if (headersImageDTO != null) {
            headersImageEntity.setCategoryId(headersImageEntity.getCategoryId());
            headersImageEntity.setName(headersImageEntity.getName());
            headersImageEntity.setCreatedAt(LocalDateTime.now());
            headersImageEntity.setIsActive(Boolean.TRUE);
            if (headersImageDTO.getImg() != null) {
                Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", ResponseMessage.CLOUD_NAME,
                        "api_key", ResponseMessage.API_KEY, "api_secret", ResponseMessage.API_SECRET));
                try {
                    Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.asMap("public_id", "header_Image" + headersImageDTO.getId()));
                    String url = uploadResult.get("url").toString();
                    headersImageEntity.setImg(url);
                    HeadersImageEntity headersImage = headersImageRepo.save(headersImageEntity);
                    map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
                    map.put(ResponseMessage.RESPONSE_DATA, headersImage);
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
        return map;
    }

    @Override
    public Map<String, Object> getHeaderById(Long id) {
        Map<String, Object> map = new HashMap<>();
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
        return map;
    }

    @Override
    public Map<String, Object> updateHeaderImg(Long id, MultipartFile multipartFile) {
        Map<String, Object> map = new HashMap<>();
        Optional<HeadersImageEntity> existHeader = headersImageRepo.findById(id);
        HeadersImageEntity headersImage = existHeader.get();
        if (existHeader.isPresent()) {
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", ResponseMessage.CLOUD_NAME,
                    "api_key", ResponseMessage.API_KEY, "api_secret", ResponseMessage.API_SECRET));
            try {
                Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.asMap("public_id", "header_Image" + id));
                String url = uploadResult.get("url").toString();
                headersImage.setImg(url);
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
        return map;
    }
}
