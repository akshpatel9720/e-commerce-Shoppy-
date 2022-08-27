package com.category.entity;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Promocode {

    private Long id;
    private String couponCode;
    private String description;
    private String type;
    private Integer minValue;
    private Integer maxDiscountValue;
    private String isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
