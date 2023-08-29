package com.nth.mike.controller;

import com.nth.mike.constant.EntityConstant;
import com.nth.mike.service.*;
import com.nth.mike.utils.NumberFormatUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({ "/mike" })
public class WebController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductCategoryService pcs;
    @Autowired
    ObjectCategoryService ocs;
    @Autowired
    SupplierService supplierService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ColorService colorService;
    @Autowired
    SizeService sizeService;

    @GetMapping({ "", "/home" })
    public String home() {
        return "views/web/home";
    }

    @GetMapping({ "/collection/shop", "/collection/shop/all" })
    public String shop(Model model) {
        model.addAttribute(EntityConstant.OBJECTCATES, ocs.findAll());
        model.addAttribute(EntityConstant.PRODUCTCATES, pcs.findAll());
        model.addAttribute(EntityConstant.EMPLOYEES, employeeService.findAll());
        model.addAttribute(EntityConstant.COLORS, colorService.findAll());
        model.addAttribute(EntityConstant.SIZES, sizeService.findAll());
        return "views/web/shop";
    }

    @GetMapping("/collection/shop/product-detail/{id}")
    public String productDetail(@PathVariable String id, Model model) {
        if (!NumberFormatUtils.isLong(id)) {
            return "views/web/home";
        }
        if (productService.findById(Long.parseLong(id)) == null) {
            return "views/web/home";
        }
        return "views/web/product-detail";
    }

    @GetMapping("/collection/shop/shop-search/{field}")
    public String shopSearch(@PathVariable String field, Model model) {
        return "views/web/shop-search";
    }

    @GetMapping("/collection/shop/shop-filter/{field}")
    public String shopFilter(@PathVariable String field, Model model) {
        return "views/web/shop-filter";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
