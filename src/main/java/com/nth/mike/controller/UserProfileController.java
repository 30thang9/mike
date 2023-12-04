package com.nth.mike.controller;

import com.nth.mike.constant.EntityConstant;
import com.nth.mike.model.dto.user.UserDTO;
import com.nth.mike.service.AreaAddressService;
import com.nth.mike.service.OrderService;
import com.nth.mike.service.ShippingAddressService;
import com.nth.mike.service.UserService;

import javax.servlet.http.HttpSession;

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
    private OrderService orderService;
    @Autowired
    private AreaAddressService areaAddressService;
    @Autowired
    private ShippingAddressService shippingAddressService;
    @Autowired
    private UserService userService;

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
    public String address(HttpSession session, Model model) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("shippingAddress",
                    shippingAddressService.findByAccount(userService.findAccountByUserName(user.getUsername())));
        }
        model.addAttribute(EntityConstant.CITY, areaAddressService.findAllCity());
        return "views/web/profile/address";
    }
}
