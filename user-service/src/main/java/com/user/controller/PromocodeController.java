package com.user.controller;

import com.user.DTO.AddressDTO;
import com.user.DTO.PromocodeDTO;
import com.user.DTO.PromocodeListDTO;
import com.user.exception.UserException;
import com.user.service.PromocodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/promocode")
public class PromocodeController {

    @Autowired
    PromocodeService promocodeService;

    @PostMapping("/savePromocode")
    public ResponseEntity<Map<String, Object>> savePromocode(@RequestBody PromocodeDTO promocodeDTO, @RequestHeader("Authorization") String authToken) {
        try {
            return new ResponseEntity<>(promocodeService.save(promocodeDTO,authToken), HttpStatus.OK);
        } catch (Exception e) {
            throw new UserException.HandleException("promocode is not saved");
        }
    }

    @DeleteMapping("/deletePromocode")
    public ResponseEntity<Map<String, Object>> deleteAddress(@RequestParam("id") Long id, @RequestHeader("Authorization") String authToken) {
        try {
            return new ResponseEntity<>(promocodeService.deleteAddress(id,authToken), HttpStatus.OK);
        } catch (Exception e) {
            throw new UserException.HandleException("promocode is not deleted");
        }
    }

    @PatchMapping("/updatePromocode")
    public ResponseEntity<Map<String, Object>> updatePromocode(@RequestParam("id") Long id, @RequestBody PromocodeDTO promocodeDTO, @RequestHeader("Authorization") String authToken) {
        try {
            return new ResponseEntity<>(promocodeService.updatePromocode(id, promocodeDTO,authToken), HttpStatus.OK);
        } catch (Exception e) {
            throw new UserException.HandleException("promocode is not update");
        }
    }

    @PostMapping("/getAllPromocode")
    public ResponseEntity<Map<String, Object>> getAllPromocode(@RequestBody PromocodeListDTO promocodeListDTO, @RequestHeader("Authorization") String authToken) {
        try {
            return new ResponseEntity<>(promocodeService.getAllPromocode(promocodeListDTO,authToken), HttpStatus.OK);
        } catch (Exception e) {
            throw new UserException.HandleException("promocode is not saved");
        }
    }


}
