package com.user.service;

import com.user.DTO.UserListDTO;
import com.user.entity.UserEntity;

import java.util.Map;

public interface UserService {

    Map<String, Object> getById(Long id, String authToken);

    Map<String, Object> update(UserEntity userEntity, String authToken);

    Map<String, Object> delete(Long id, String authToken);

    Map<String, Object> updateEmail(Long id, String oldEmail, String newEmail, String authToken);

    Map<String, Object> getUser(UserListDTO userListDTO, String authToken);

    Map<String, Object> getAllUser(String authToken);
}
