package com.paymentservice.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseDTO
{
    private Boolean status;
    private String message;
    private Object data;
}
