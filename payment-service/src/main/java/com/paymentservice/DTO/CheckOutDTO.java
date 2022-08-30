package com.paymentservice.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CheckOutDTO {

    private String productName;
    private int quantity;
    private int price;
    private long productId;
    private long userId;
}
