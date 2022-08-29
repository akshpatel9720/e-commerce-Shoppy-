package com.category.service;

import com.category.DTO.ResponseDTO;

public interface AuthenticationService {
    public ResponseDTO isAuthenticated(String authToken);
}
