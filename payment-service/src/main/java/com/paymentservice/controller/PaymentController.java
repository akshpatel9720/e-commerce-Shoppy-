package com.paymentservice.controller;

import com.paymentservice.DTO.CheckOutDTO;
import com.paymentservice.entity.StripeResponse;
import com.paymentservice.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Autowired
    OrderService orderService;

    @PostMapping("/checkoutList")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckOutDTO> checkOutDto, @RequestParam("userId") long userId, @RequestHeader("Authorization") String token) throws StripeException {
        Session session = orderService.checkoutList(checkOutDto, userId,token);
        StripeResponse stripeResponse = new StripeResponse(session.getUrl(), session.getId(), session.getPaymentIntent());

        return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
    }
}
