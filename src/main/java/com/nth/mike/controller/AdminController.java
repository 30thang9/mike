package com.nth.mike.controller;

import com.nth.mike.constant.EntityConstant;
import com.nth.mike.entity.DiscountStatus;
import com.nth.mike.entity.ProductStatus;
import com.nth.mike.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/mike/admin"})
public class AdminController {
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

    @GetMapping({"","/home"})
    public String getHomeAdmin() {
        return "views/admin/home-admin";
    }

    @GetMapping({"/product/list"})
    public String getProductList() {
        return "views/admin/product-list";
    }

    @GetMapping({"/product/add"})
    public String getProductAdd(Model model) {
        model.addAttribute(EntityConstant.PRODUCTS,productService.findAll());
        model.addAttribute(EntityConstant.PRODUCTCATES,pcs.findAll());
        model.addAttribute(EntityConstant.OBJECTCATES,ocs.findAll());
        return "views/admin/product-add";
    }
    @GetMapping({"/product/edit/{id}"})
    public  String getProductEdit(@PathVariable Long id, Model model) {
        model.addAttribute(EntityConstant.PRODUCTS,productService.findById(id));
        model.addAttribute(EntityConstant.PRODUCTCATES,pcs.findAll());
        model.addAttribute(EntityConstant.OBJECTCATES,ocs.findAll());
        model.addAttribute(EntityConstant.PRODUCTSTATUS, ProductStatus.values());
        model.addAttribute(EntityConstant.DISCOUNTSTATUS, DiscountStatus.values());
        return "views/admin/product-edit";
    }

    @GetMapping({"/product/detail/{id}"})
    public  String getProductDetail(@PathVariable Long id, Model model) {
        model.addAttribute(EntityConstant.PRODUCTS,productService.findById(id));
        model.addAttribute(EntityConstant.PRODUCTCATES,pcs.findAll());
        model.addAttribute(EntityConstant.OBJECTCATES,ocs.findAll());
        return "views/admin/product-detail";
    }

    @GetMapping({"/invoice-purchase/add"})
    public String getInvoicePurchaseAdd(Model model) {
        model.addAttribute(EntityConstant.PRODUCTS,productService.findAll());
        model.addAttribute(EntityConstant.PRODUCTCATES,pcs.findAll());
        model.addAttribute(EntityConstant.OBJECTCATES,ocs.findAll());
        model.addAttribute(EntityConstant.SUPPLIERS,supplierService.findAll());
        model.addAttribute(EntityConstant.EMPLOYEES,employeeService.findAll());
        model.addAttribute(EntityConstant.COLORS,colorService.findAll());
        model.addAttribute(EntityConstant.SIZES,sizeService.findAll());
        model.addAttribute(EntityConstant.MATERIALS,materialService.findAll());
        return "views/admin/invoicePurchase-add";
    }

    @GetMapping({"/test"})
    public String getTest(Model model) {
        model.addAttribute(EntityConstant.PRODUCTS,productService.findAll());
        return "views/admin/test";
    }
}
