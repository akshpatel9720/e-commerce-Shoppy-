package com.user.controller;

import com.user.DTO.AddressDTO;
import com.user.DTO.AddressListDTO;
import com.user.DTO.UserListDTO;
import com.user.entity.UserEntity;
import com.user.exception.UserException;
import com.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping("/saveAddress")
    public ResponseEntity<Map<String, Object>> save(@RequestBody AddressDTO addressDTO, @RequestHeader("Authorization") String authToken) {
        try {
            return new ResponseEntity<>(addressService.save(addressDTO, authToken), HttpStatus.OK);
        } catch (Exception e) {
            throw new UserException.HandleException("address is not saved");
        }
    }

    @DeleteMapping("/deleteAddress")
    public ResponseEntity<Map<String, Object>> deleteAddress(@RequestParam("userId") Long userId, @RequestHeader("Authorization") String authToken) {
        try {
            return new ResponseEntity<>(addressService.deleteAddress(userId, authToken), HttpStatus.OK);
        } catch (Exception e) {
            throw new UserException.HandleException("address is not saved");
        }
    }

    @PostMapping("/getListAddress")
    public ResponseEntity<Map<String, Object>> getAddresss(@RequestBody AddressListDTO addressListDTO, @RequestHeader("Authorization") String authToken) {
        try {
            return new ResponseEntity<>(addressService.getAddress(addressListDTO, authToken), HttpStatus.OK);
        } catch (Exception e) {
            throw new UserException.getAllUser("data is not fetched");
        }
    }

    @PatchMapping("/updateAddress")
    public ResponseEntity<Map<String, Object>> updateAddress(@RequestParam("id") Long id, @RequestBody AddressDTO addressDTO, @RequestHeader("Authorization") String authToken) {
        try {
            return new ResponseEntity<>(addressService.updateAddress(id, addressDTO, authToken), HttpStatus.OK);
        } catch (Exception e) {
            throw new UserException.getAllUser("data is not fetched");
        }
    }

}
