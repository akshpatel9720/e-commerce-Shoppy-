package com.user.DTO;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {

    Map<String, Object> where = new HashMap<>();
    Map<String, Object> pagination = new HashMap<>();


}
