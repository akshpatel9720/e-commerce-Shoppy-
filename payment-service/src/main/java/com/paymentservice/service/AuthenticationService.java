package com.paymentservice.service;

import com.paymentservice.DTO.ResponseDTO;

public interface AuthenticationService {
    public ResponseDTO isAuthenticated(String authToken);
}
