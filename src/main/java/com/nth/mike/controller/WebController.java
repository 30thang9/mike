package com.nth.mike.controller;

import com.nth.mike.constant.EntityConstant;
import com.nth.mike.entity.AccountType;
import com.nth.mike.entity.Order;
import com.nth.mike.entity.PaymentMethod;
import com.nth.mike.entity.RoleName;
import com.nth.mike.entity.other.District;
import com.nth.mike.entity.other.Ward;
import com.nth.mike.model.cart.CartOrder;
import com.nth.mike.model.dto.checkout.AddressCheckoutSession;
import com.nth.mike.service.*;
import com.nth.mike.utils.NumberFormatUtils;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping({ "/mike" })
public class WebController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService pcs;
    @Autowired
    private ObjectCategoryService ocs;
    @Autowired
    private UserService userService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private SizeService sizeService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AreaAddressService areaAddressService;
    @Autowired
    private DeliveryService deliverService;

    @GetMapping({ "", "/home" })
    public String home() {
        return "views/web/home";
    }

    @GetMapping({ "/collection/shop", "/collection/shop/all" })
    public String shop(Model model) {
        model.addAttribute(EntityConstant.OBJECTCATES, ocs.findAll());
        model.addAttribute(EntityConstant.PRODUCTCATES, pcs.findAll());
        model.addAttribute(EntityConstant.EMPLOYEES,
                userService.findAccountByRole(userService.findRoleByName(RoleName.ROLE_ADMIN_SALES)));
        model.addAttribute(EntityConstant.COLORS, colorService.findAll());
        model.addAttribute(EntityConstant.SIZES, sizeService.findAll());
        return "views/web/shop";
    }

    @GetMapping("/collection/shop/product-detail/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        // if (productService.(id) == null) {
        // return "views/web/home";
        // }
        model.addAttribute(EntityConstant.PRODUCTS,
                productService.findById(id));
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

    @GetMapping("/cart/detail")
    public String getCartDetail(HttpSession session, Model model) {
        // CartOrder cart = (CartOrder) session.getAttribute("cart");
        // if (cart != null) {
        // if (cart.getId() != null) {
        // Order order = orderService.findById(cart.getId());
        // if (order != null) {
        // model.addAttribute("deli", order.getDelivery());
        // }
        // }
        // }
        return "views/web/shopping-cart";
    }

    @GetMapping("/cart/invalid-item")
    public String getInvalidItem() {
        return "views/web/invalid-cartItem";
    }

    @GetMapping("/cart/checkout-address")
    public String getCheckoutAddress(HttpSession session, Model model) {
        CartOrder cart = (CartOrder) session.getAttribute("cart");
        List<District> di = areaAddressService.findAllDistrict();
        List<Ward> wa = areaAddressService.findAllWard();
        if (cart != null) {
            if (cart.getId() != null) {
                Order order = orderService.findById(cart.getId());
                if (order != null) {
                    AddressCheckoutSession addressId = (AddressCheckoutSession) session.getAttribute("addressSec");
                    if (addressId != null) {
                        di = areaAddressService
                                .findDistrictByCity(areaAddressService.findCityById(addressId.getCityId()));
                        wa = areaAddressService
                                .findWardByDistrict(areaAddressService.findDistrictById(addressId.getDistrictId()));
                    }
                    model.addAttribute("deli", order.getDelivery());
                }
            }
        }
        model.addAttribute(EntityConstant.DISTRICTS, di);
        model.addAttribute(EntityConstant.WARDS, wa);
        model.addAttribute(EntityConstant.CITY, areaAddressService.findAllCity());
        return "views/web/checkout-address";
    }

    @GetMapping("/cart/checkout-payment")
    public String getCheckoutPayment(HttpSession session, Model model) {
        CartOrder cart = (CartOrder) session.getAttribute("cart");
        if (cart != null) {
            if (cart.getId() != null) {
                Order order = orderService.findById(cart.getId());
                if (order != null) {
                    model.addAttribute("deli", order.getDelivery());
                }
            }
        }
        model.addAttribute("payment_method", PaymentMethod.values());
        model.addAttribute(EntityConstant.DELIVERY, deliverService.findAll());
        System.out.println(deliverService.findAll());
        return "views/web/checkout-payment-method";
    }

    @GetMapping("/cart/checkout-final")
    public String getCheckoutPaymentFinal(HttpSession session, Model model) {
        CartOrder cart = (CartOrder) session.getAttribute("cart");
        if (cart != null) {
            if (cart.getId() != null) {
                Order order = orderService.findById(cart.getId());
                if (order != null) {
                    model.addAttribute("deli", order.getDelivery());
                }
            }
        }
        return "views/web/checkout-over-review";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
