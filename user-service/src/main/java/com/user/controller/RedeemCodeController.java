package com.user.controller;

import com.user.DTO.PromocodeDTO;
import com.user.DTO.PromocodeListDTO;
import com.user.DTO.RedeemCodeDTO;
import com.user.DTO.RedeemCodeListDTO;
import com.user.exception.UserException;
import com.user.service.RedeemCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/redeemcode")
public class RedeemCodeController {
    @Autowired
    RedeemCodeService redeemCodeService;

    @PostMapping("/addUserRedeemCode")
    public ResponseEntity<Map<String, Object>> addUserRedeemCode(@RequestBody RedeemCodeDTO redeemCodeDTO, @RequestHeader("Authorization") String authToken) {
        try {
            return new ResponseEntity<>(redeemCodeService.addUserRedeemCode(redeemCodeDTO, authToken), HttpStatus.OK);
        } catch (Exception e) {
            throw new UserException.HandleException("promocode is not saved");
        }
    }

    @PatchMapping("/updateRedeemCode")
    public ResponseEntity<Map<String, Object>> updateRedeemCode(@RequestParam("id") Long id, @RequestBody RedeemCodeDTO redeemCodeDTO, @RequestHeader("Authorization") String authToken) {
        try {
            return new ResponseEntity<>(redeemCodeService.updatePromocode(id, redeemCodeDTO, authToken), HttpStatus.OK);
        } catch (Exception e) {
            throw new UserException.HandleException("promocode is not update");
        }
    }

    @PostMapping("/getAllRedeemCode")
    public ResponseEntity<Map<String, Object>> getAllRedeemCode(@RequestBody RedeemCodeListDTO redeemCodeListDTO, @RequestHeader("Authorization") String authToken) {
        try {
            return new ResponseEntity<>(redeemCodeService.getAllRedeemCode(redeemCodeListDTO, authToken), HttpStatus.OK);
        } catch (Exception e) {
            throw new UserException.HandleException("promocode is not saved");
        }
    }
}
