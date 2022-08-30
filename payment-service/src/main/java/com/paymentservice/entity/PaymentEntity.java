package com.paymentservice.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private Long userId;
    private String status;
    private Boolean isActive;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Float amount;
    private String currencyType;
    private Integer quantity;
    private String pname;

}
