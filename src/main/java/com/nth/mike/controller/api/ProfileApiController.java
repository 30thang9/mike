package com.nth.mike.controller.api;

import java.util.List;

import com.nth.mike.constant.StatusConstant;
import com.nth.mike.model.response.ShippingAddressRequest;
import com.nth.mike.model.response.shared.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nth.mike.entity.Account;
import com.nth.mike.entity.ShippingAddress;
import com.nth.mike.service.ShippingAddressService;
import com.nth.mike.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/mike/api/user")
public class ProfileApiController {
    @Autowired
    private ShippingAddressService shippingAddressService;
    @Autowired
    private UserService userService;

    @GetMapping("/shipping-address/{userId}")
    public ResponseEntity<List<ShippingAddress>> getShippingAddress(@PathVariable(value = "userId") Long userId) {
        Account account = userService.findAccountById(userId);
        return ResponseEntity.ok(shippingAddressService.findByAccount(account));
    }

    @GetMapping("/shipping-address/{username}")
    public ResponseEntity<List<ShippingAddress>> getShippingAddresses(@PathVariable(value = "username") String username) {
        Account account = userService.findAccountByUserName(username);
        return ResponseEntity.ok(shippingAddressService.findByAccount(account));
    }

    @PostMapping("/shipping-address/add")
    public ResponseEntity<?> addShippingAddress(@RequestBody @Valid ShippingAddressRequest request){
        return ResponseEntity.ok(new BasicResponse(StatusConstant.SUCCESS,"Add shipping address successfully!"));
    }

}
