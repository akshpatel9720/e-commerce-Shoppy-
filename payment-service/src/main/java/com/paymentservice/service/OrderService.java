package com.paymentservice.service;

import com.paymentservice.DTO.CheckOutDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import java.util.List;

public interface OrderService {
    Session checkoutList(List<CheckOutDTO> checkOutDto, long userId, String token) throws StripeException;
}
