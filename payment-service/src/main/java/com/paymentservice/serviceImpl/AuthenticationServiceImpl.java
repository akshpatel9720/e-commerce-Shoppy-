package com.paymentservice.serviceImpl;


import com.paymentservice.DTO.ResponseDTO;
import com.paymentservice.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    RestTemplate restTemplate;

    @Override
    public ResponseDTO isAuthenticated(String authToken) {
        ResponseEntity<Object> responseEntity = null;

        try {
            String URL = "http://localhost:9000/api/v1/user/getAllUser";
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            httpHeaders.set("Authorization", authToken);
//            httpHeaders.add("user-agent", "PostmanRuntime/7.29.2");
            HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(httpHeaders);
            responseEntity = restTemplate.exchange(URL, HttpMethod.GET, httpEntity, Object.class);
        } catch (Exception e) {
            return new ResponseDTO(false, "auth failed", e);
        }
        return new ResponseDTO(true, "success", responseEntity);
    }
}
