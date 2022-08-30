package com.paymentservice.serviceImpl;

import com.paymentservice.DTO.CheckOutDTO;
import com.paymentservice.DTO.ResponseDTO;
import com.paymentservice.DTO.ResponseMessage;
import com.paymentservice.entity.OrderEntity;
import com.paymentservice.repository.OrderRepo;
import com.paymentservice.service.AuthenticationService;
import com.paymentservice.service.OrderService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${stripe.apikey}")
    String stripeKey;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Override
    public Session checkoutList(List<CheckOutDTO> checkOutDtoList, long userId, String token) throws StripeException {
        Map<String, Object> map = new HashMap<>();
        Session session = null;
        ResponseDTO authReponse = authenticationService.isAuthenticated(token);
        System.out.println("authresponse-----" + authReponse);
        if (authReponse.getStatus()) {
            Stripe.apiKey = stripeKey;
            List<SessionCreateParams.LineItem> sessionItemList = new ArrayList<>();

            for (CheckOutDTO checkOutDto : checkOutDtoList) {
                sessionItemList.add(createSessionLineItem(checkOutDto));
            }
            //in stripe
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setCancelUrl("https://example.com/cancel")
                    .setSuccessUrl("https://example.com/success")
                    .addAllLineItem(sessionItemList)
                    .build();
            session = Session.create(params);
            session.getPaymentIntent();
            System.out.println(session.getPaymentIntent());
            //save in database
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setSessionId(session.getId());
            orderEntity.setTotalprice(session.getAmountTotal() / 100);
            orderEntity.setUserId(userId);
            orderEntity.setPaymentId(session.getPaymentIntent());
            orderEntity.setCreatedAt(LocalDateTime.now());
            orderRepo.save(orderEntity);
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_401);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.AUTHENTICATION_FAIL);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return session;
    }


    private SessionCreateParams.LineItem createSessionLineItem(CheckOutDTO checkOutDto) {
        return SessionCreateParams.LineItem.builder()
                .setPriceData(createPriceData(checkOutDto))
                .setQuantity((long) checkOutDto.getQuantity())
                .build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(CheckOutDTO checkOutDto) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("INR")
                .setUnitAmount((long) (checkOutDto.getPrice() * 100))
                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(checkOutDto.getProductName()).build()).build();
    }

}
