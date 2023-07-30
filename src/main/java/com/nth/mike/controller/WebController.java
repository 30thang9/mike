package com.nth.mike.controller;

import com.nth.mike.constant.EntityConstant;
import com.nth.mike.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Autowired
    MaterialService materialService;

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
        model.addAttribute(EntityConstant.MATERIALS, materialService.findAll());
        return "views/web/shop";
    }

    @GetMapping("/collection/shop/product-detail/{id}")
    public String productDetail(@PathVariable String id, Model model) {
        return "views/web/product-detail";
    }

    @GetMapping("/collection/shop/shop-cate/{field}")
    public String shopCate(@PathVariable String field, Model model) {
        return "views/web/shop-category";
    }

    @GetMapping("/collection/shop/shop-filter")
    public String shopFilter(@RequestParam String filter, Model model) {
        return "views/web/-shop-filter";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
