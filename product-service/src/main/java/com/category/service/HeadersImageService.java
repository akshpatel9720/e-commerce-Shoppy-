package com.category.service;

import com.category.DTO.HeaderImageListDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface HeadersImageService {

    Map<String, Object> save(String headersImageDTO, MultipartFile multipartFile, String token) throws JsonProcessingException;

    Map<String, Object> getHeaderById(Long id, String token);

    Map<String, Object> updateHeaderImg(Long id, MultipartFile multipartFile, String token);

    Map<String, Object> getHeaderList(HeaderImageListDTO headerImageListDTO, String token);
}
