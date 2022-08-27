package com.user.controller;

import com.user.DTO.UserListDTO;
import com.user.entity.UserEntity;
import com.user.exception.UserException;
import com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping("/getById")
    public ResponseEntity<Map<String, Object>> getById(@RequestParam("id") Long id) {
        try {
            return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
        } catch (Exception e) {
            throw new UserException.GetUserByIdHandler("data does not fetch");
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<Map<String, Object>> update(@RequestBody UserEntity userEntity) {
        try {
            return new ResponseEntity<>(userService.update(userEntity), HttpStatus.OK);
        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            throw new UserException.UpdateHandler("data is not update");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> delete(@RequestParam("id") Long id) {
        try {
            return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            throw new UserException.DeleteHandler("data does not delete");
        }
    }

    @PostMapping("/updateEmail")
    public ResponseEntity<Map<String, Object>> updateEmail(@RequestParam("id") Long id, @RequestParam("oldemail") String oldEmail, @RequestParam("newemail") String newEmail) {
        try {
            return new ResponseEntity<>(userService.updateEmail(id, oldEmail, newEmail), HttpStatus.OK);
        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            throw new UserException.UpdateEamilHandler("data is not update");
        }
    }

    @PostMapping("/getUser")
    public ResponseEntity<Map<String, Object>> getUser(@RequestBody UserListDTO userListDTO) {
        try {
            return new ResponseEntity<>(userService.getUser(userListDTO), HttpStatus.OK);
        } catch (Exception e) {
            throw new UserException.getAllUser("data is not fetched");
        }
    }


}
