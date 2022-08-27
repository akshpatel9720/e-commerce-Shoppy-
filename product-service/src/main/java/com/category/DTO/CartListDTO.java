package com.category.DTO;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartListDTO
{
    Map<String,Object> where=new HashMap<>();
    Map<String,Object> pagination=new HashMap<>();
}
