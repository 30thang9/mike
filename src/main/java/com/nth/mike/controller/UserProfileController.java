package com.nth.mike.controller;

import com.nth.mike.service.SaleDetailService;
import com.nth.mike.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mike/user-profile")
public class UserProfileController {
    @Autowired
    private SaleService saleService;
    @Autowired
    private SaleDetailService saleDetailService;

    @GetMapping("/profile")
    public String profile() {
        return "views/web/profile/profile";
    }
    @GetMapping("/order")
    public String order() {
        return "views/web/profile/order";
    }
    @GetMapping("/order-detail/{id}")
    public String profile(@PathVariable String id, Model model) {

        return "views/web/profile/order-detail";
    }
    @GetMapping("/address")
    public String address() {
        return "views/web/profile/address";
    }
}
