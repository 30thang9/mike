package com.nth.mike.controller;

import com.nth.mike.constant.EntityConstant;
import com.nth.mike.entity.*;
import com.nth.mike.model.dto.product.ProductDetailDTO;
import com.nth.mike.model.dto.product.ProductDetailListSizeDTO;
import com.nth.mike.model.dto.product.ProductFullDetailDTO;
import com.nth.mike.model.mapper.product.ProductDetailListSizeMapper;
import com.nth.mike.service.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private SizeImageService sizeImageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private SizeService sizeService;
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
        // model.addAttribute(EntityConstant.PRODUCTS, productService.findAll());
        model.addAttribute(EntityConstant.PRODUCTCATES, pcs.findAll());
        model.addAttribute(EntityConstant.OBJECTCATES, ocs.findAll());
        model.addAttribute(EntityConstant.SIZEIMAGES, sizeImageService.findAll());
        model.addAttribute(EntityConstant.COLORS, colorService.findAll());
        model.addAttribute(EntityConstant.SIZES, sizeService.findAll());
        model.addAttribute(EntityConstant.PRODUCTDETAILSTATUS, ProductDetailStatus.values());
        model.addAttribute(EntityConstant.PRODUCTSTATUS, ProductStatus.values());
        model.addAttribute(EntityConstant.DISCOUNTSTATUS, DiscountStatus.values());
        model.addAttribute(EntityConstant.BESTSELLERSTATUS, BestSellerStatus.values());
        model.addAttribute(EntityConstant.HOTSTATUS, HotStatus.values());
        return "views/admin/product-add";
    }

    @GetMapping({ "/product/edit/{id}" })
    public String getProductEdit(@PathVariable Long id, Model model) {
        ProductFullDetailDTO productFullDetailDTO = productService
                .findProductFullDetailByProduct(productService.findById(id));
        model.addAttribute(EntityConstant.PRODUCTS, productFullDetailDTO.getProduct());
        model.addAttribute(EntityConstant.PRODUCTCATES, pcs.findAll());
        model.addAttribute(EntityConstant.OBJECTCATES, ocs.findAll());
        model.addAttribute(EntityConstant.SIZEIMAGES, sizeImageService.findAll());
        model.addAttribute(EntityConstant.SIZES, sizeService.findAll());
        model.addAttribute(EntityConstant.PRODUCTDETAILSTATUS, ProductDetailStatus.values());
        model.addAttribute(EntityConstant.PRODUCTSTATUS, ProductStatus.values());
        model.addAttribute(EntityConstant.DISCOUNTSTATUS, DiscountStatus.values());
        model.addAttribute(EntityConstant.BESTSELLERSTATUS, BestSellerStatus.values());
        model.addAttribute(EntityConstant.HOTSTATUS, HotStatus.values());

        List<Color> colors = colorService.findAll();
        List<Color> colors2 = new ArrayList<>();
        Set<Color> uniqueColors = new HashSet<>();
        List<ProductDetailDTO> uniqueProductDetails = new ArrayList<>();

        for (ProductDetailDTO productDetailDTO : productFullDetailDTO.getProductDetail()) {
            if (!uniqueColors.contains(productDetailDTO.getColor())) {
                uniqueColors.add(productDetailDTO.getColor());
                uniqueProductDetails.add(productDetailDTO);
            }
        }

        List<ProductDetailListSizeDTO> productDetails = new ArrayList<>();

        for (ProductDetailDTO productDetailDTO : uniqueProductDetails) {
            List<ProductDetailDTO> productDetailDTOs = productFullDetailDTO.getProductDetail()
                    .stream()
                    .filter(dto -> dto.getColor().equals(productDetailDTO.getColor()))
                    .collect(Collectors.toList());

            productDetails
                    .add(ProductDetailListSizeMapper.toProductDetailListSizeDTOFromProductDetailDTO(productDetailDTOs));
        }

        for (Color c : colors) {
            if (!uniqueColors.contains(c)) {
                colors2.add(c);
            }
        }

        model.addAttribute(EntityConstant.COLORS, colors2);

        model.addAttribute(EntityConstant.PRODUCTDETAILS, productDetails);

        return "views/admin/product-edit";
    }

    // @GetMapping({ "/product/detail/{id}" })
    // public String getProductDetail(@PathVariable Long id, Model model) {
    // model.addAttribute(EntityConstant.PRODUCTS, productService.findById(id));
    // model.addAttribute(EntityConstant.PRODUCTDETAILS,
    // productDetailService.findByProduct(productService.findById(id)));
    // model.addAttribute(EntityConstant.PRODUCTCATES, pcs.findAll());
    // model.addAttribute(EntityConstant.OBJECTCATES, ocs.findAll());
    // model.addAttribute(EntityConstant.COLORS, colorService.findAll());
    // model.addAttribute(EntityConstant.SIZES, sizeService.findAll());
    // model.addAttribute("setColors",
    // getColorListNotDuplicates(productDetailService.findByProduct(productService.findById(id))));
    // model.addAttribute("setSizes",
    // getSizeListNotDuplicates(productDetailService.findByProduct(productService.findById(id))));
    // return "views/admin/product-detail";
    // }

    @GetMapping({ "/invoice-purchase/add" })
    public String getInvoicePurchaseAdd(Model model) {
        model.addAttribute(EntityConstant.PRODUCTS, productService.findAll());
        model.addAttribute(EntityConstant.PRODUCTCATES, pcs.findAll());
        model.addAttribute(EntityConstant.OBJECTCATES, ocs.findAll());
        model.addAttribute(EntityConstant.SUPPLIERS,
                userService.findAccountByRole(userService.findRoleByName(RoleName.ROLE_USER)));
        model.addAttribute(EntityConstant.EMPLOYEES,
                userService.findAccountByRole(userService.findRoleByName(RoleName.ROLE_ADMIN_INVENTORY)));
        model.addAttribute(EntityConstant.COLORS, colorService.findAll());
        model.addAttribute(EntityConstant.SIZES, sizeService.findAll());
        return "views/admin/invoicePurchase-add";
    }

    @GetMapping({ "/invoice-purchase/edit/{purchaseId}" })
    public String getInvoicePurchaseEdit(Model model, @PathVariable String purchaseId) {
        model.addAttribute(EntityConstant.PRODUCTS, productService.findAll());
        model.addAttribute(EntityConstant.PRODUCTCATES, pcs.findAll());
        model.addAttribute(EntityConstant.OBJECTCATES, ocs.findAll());
        model.addAttribute(EntityConstant.SUPPLIERS,
                userService.findAccountByRole(userService.findRoleByName(RoleName.ROLE_USER)));
        model.addAttribute(EntityConstant.EMPLOYEES,
                userService.findAccountByRole(userService.findRoleByName(RoleName.ROLE_ADMIN_INVENTORY)));
        model.addAttribute(EntityConstant.COLORS, colorService.findAll());
        model.addAttribute(EntityConstant.SIZES, sizeService.findAll());
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

    private List<Color> getColorListNotDuplicates(List<ProductDetail> productDetails) {
        Set<Color> colorList = new HashSet<>();
        for (ProductDetail productDetail : productDetails) {
            colorList.add(productDetail.getColor());
        }

        List<Color> sortedColors = colorList.stream()
                .sorted(Comparator.comparingLong(c -> c.getId()))
                .toList();

        return sortedColors;
    }

    private List<Size> getSizeListNotDuplicates(List<ProductDetail> productDetails) {
        Set<Size> sizeList = new HashSet<>();
        for (ProductDetail productDetail : productDetails) {
            sizeList.add(productDetail.getSize());
        }
        List<Size> sortedSizes = sizeList.stream()
                .sorted(Comparator.comparingLong(c -> c.getId()))
                .toList();
        return sortedSizes;
    }
}
