package com.nth.mike.controller;

import com.nth.mike.constant.EntityConstant;
import com.nth.mike.entity.BestSellerStatus;
import com.nth.mike.entity.DiscountStatus;
import com.nth.mike.entity.HotStatus;
import com.nth.mike.entity.ProductStatus;
import com.nth.mike.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({ "/mike/admin" })
public class AdminController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductCategoryService pcs;
    @Autowired
    private ObjectCategoryService ocs;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private SizeService sizeService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @GetMapping({ "", "/home" })
    public String getHomeAdmin() {
        return "views/admin/home-admin";
    }

    @GetMapping({ "/product/list" })
    public String getProductList() {
        return "views/admin/product-list";
    }

    @GetMapping({ "/product/add" })
    public String getProductAdd(Model model) {
        model.addAttribute(EntityConstant.PRODUCTS, productService.findAll());
        model.addAttribute(EntityConstant.PRODUCTCATES, pcs.findAll());
        model.addAttribute(EntityConstant.OBJECTCATES, ocs.findAll());
        model.addAttribute(EntityConstant.PRODUCTSTATUS, ProductStatus.values());
        model.addAttribute(EntityConstant.DISCOUNTSTATUS, DiscountStatus.values());
        model.addAttribute(EntityConstant.BESTSELLERSTATUS, BestSellerStatus.values());
        model.addAttribute(EntityConstant.HOTSTATUS, HotStatus.values());
        return "views/admin/product-add";
    }

    @GetMapping({ "/product/edit/{id}" })
    public String getProductEdit(@PathVariable Long id, Model model) {
        model.addAttribute(EntityConstant.PRODUCTS, productService.findById(id));
        model.addAttribute(EntityConstant.PRODUCTCATES, pcs.findAll());
        model.addAttribute(EntityConstant.OBJECTCATES, ocs.findAll());
        model.addAttribute(EntityConstant.PRODUCTSTATUS, ProductStatus.values());
        model.addAttribute(EntityConstant.DISCOUNTSTATUS, DiscountStatus.values());
        model.addAttribute(EntityConstant.BESTSELLERSTATUS, BestSellerStatus.values());
        model.addAttribute(EntityConstant.HOTSTATUS, HotStatus.values());
        return "views/admin/product-edit";
    }

    @GetMapping({ "/product/detail/{id}" })
    public String getProductDetail(@PathVariable Long id, Model model) {
        model.addAttribute(EntityConstant.PRODUCTS, productService.findById(id));
        model.addAttribute(EntityConstant.PRODUCTDETAILS,
                productDetailService.findByProduct(productService.findById(id)));
        model.addAttribute(EntityConstant.PRODUCTCATES, pcs.findAll());
        model.addAttribute(EntityConstant.OBJECTCATES, ocs.findAll());
        model.addAttribute(EntityConstant.PRODUCTIMAGES,
                productImageService.findByProduct(productService.findById(id)));
        return "views/admin/product-detail";
    }

    @GetMapping({ "/invoice-purchase/add" })
    public String getInvoicePurchaseAdd(Model model) {
        model.addAttribute(EntityConstant.PRODUCTS, productService.findAll());
        model.addAttribute(EntityConstant.PRODUCTCATES, pcs.findAll());
        model.addAttribute(EntityConstant.OBJECTCATES, ocs.findAll());
        model.addAttribute(EntityConstant.SUPPLIERS, supplierService.findAll());
        model.addAttribute(EntityConstant.EMPLOYEES, employeeService.findAll());
        model.addAttribute(EntityConstant.COLORS, colorService.findAll());
        model.addAttribute(EntityConstant.SIZES, sizeService.findAll());
        model.addAttribute(EntityConstant.MATERIALS, materialService.findAll());
        return "views/admin/invoicePurchase-add";
    }

    @GetMapping({ "/invoice-purchase/edit/{purchaseId}" })
    public String getInvoicePurchaseEdit(Model model, @PathVariable String purchaseId) {
        model.addAttribute(EntityConstant.PRODUCTS, productService.findAll());
        model.addAttribute(EntityConstant.PRODUCTCATES, pcs.findAll());
        model.addAttribute(EntityConstant.OBJECTCATES, ocs.findAll());
        model.addAttribute(EntityConstant.SUPPLIERS, supplierService.findAll());
        model.addAttribute(EntityConstant.EMPLOYEES, employeeService.findAll());
        model.addAttribute(EntityConstant.COLORS, colorService.findAll());
        model.addAttribute(EntityConstant.SIZES, sizeService.findAll());
        model.addAttribute(EntityConstant.MATERIALS, materialService.findAll());
        model.addAttribute(EntityConstant.PURCHASES, purchaseService.findById(Long.parseLong(purchaseId)));
        model.addAttribute(EntityConstant.PURCHASEDETAILS,
                purchaseDetailService.findByPurchase(purchaseService.findById(Long.parseLong(purchaseId))));
        return "views/admin/invoicePurchase-edit";
    }

    @GetMapping({ "/invoice-purchase/list" })
    public String getPurchaseList() {
        return "views/admin/invoicePurchase-list";
    }

    @GetMapping({ "/test" })
    public String getTest(Model model) {
        model.addAttribute(EntityConstant.PRODUCTS, productService.findAll());
        return "views/admin/test";
    }
}
