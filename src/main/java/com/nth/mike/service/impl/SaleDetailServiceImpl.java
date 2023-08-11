package com.nth.mike.service.impl;

import com.nth.mike.repository.SaleDetailRepo;
import com.nth.mike.service.SaleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class SaleDetailServiceImpl implements SaleDetailService {
    @Autowired
    private SaleDetailRepo saleDetailRepo;
}
