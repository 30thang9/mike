package com.nth.mike.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nth.mike.entity.Account;
import com.nth.mike.entity.ShippingAddress;
import com.nth.mike.repository.ShippingAddressRepo;
import com.nth.mike.service.ShippingAddressService;

@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

    @Autowired
    private ShippingAddressRepo shippingAddressRepo;

    @Override
    public ShippingAddress save(ShippingAddress account) {
        return shippingAddressRepo.save(account);
    }

    @Override
    public List<ShippingAddress> findByAccount(Account account) {
        return shippingAddressRepo.findByAccount(account);
    }

    @Override
    public ShippingAddress findById(Long id) {
        return shippingAddressRepo.findById(id).orElse(null);
    }

    @Override
    public Long deleteById(Long id) {
        try {
            shippingAddressRepo.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
