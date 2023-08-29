package com.nth.mike.service;

import java.util.List;

import com.nth.mike.entity.Account;
import com.nth.mike.entity.ShippingAddress;

public interface ShippingAddressService {
    ShippingAddress save(ShippingAddress account);

    List<ShippingAddress> findByAccount(Account account);

    ShippingAddress findById(Long id);

    Long deleteById(Long id);
}
