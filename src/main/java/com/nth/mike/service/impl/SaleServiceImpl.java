package com.nth.mike.service.impl;

import com.nth.mike.repository.SaleRepo;
import com.nth.mike.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleServiceImpl implements SaleService {
    @Autowired
    private SaleRepo saleRepo;
}
