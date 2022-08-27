package com.category.service;

import com.category.DTO.HeadersImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface HeadersImageService {

    Map<String, Object> save(HeadersImageDTO headersImageDTO, MultipartFile multipartFile);

    Map<String,Object> getHeaderById(Long id);

    Map<String,Object> updateHeaderImg(Long id, MultipartFile multipartFile);
}
