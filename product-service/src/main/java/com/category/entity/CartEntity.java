package com.category.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;

    @OneToMany(mappedBy = "pId")
    private List<ProductEntity> cartDetail;

    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
