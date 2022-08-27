package com.category.DTO;


import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductListDTO {

    Map<String,Object> where=new HashMap<>();
    Map<String,Object> pagination=new HashMap<>();

}
