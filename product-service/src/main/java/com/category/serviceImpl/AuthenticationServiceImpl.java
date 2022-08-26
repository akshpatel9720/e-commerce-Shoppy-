package com.category.serviceImpl;

import com.category.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    RestTemplate restTemplate;

    @Override
    public Boolean isAuthenticated(String authToken) {
        ResponseEntity<Object> responseEntity = null;
        String URL = "http://localhost:9000/api/v1/user/getAllUser";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.set("Authorization", authToken);
        httpHeaders.add("user-agent", "PostmanRuntime/7.29.2");
        HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(httpHeaders);
        responseEntity=  restTemplate.exchange(URL, HttpMethod.POST, httpEntity, Object.class);
//        return responseEntity.getStatusCode();
        return null;
    }
}
