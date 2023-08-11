package com.nth.mike.controller.api;

import javax.servlet.http.HttpSession;

import com.nth.mike.entity.*;
import com.nth.mike.model.cart.*;
import com.nth.mike.service.OrderDetailService;
import com.nth.mike.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.nth.mike.constant.StatusConstant;
import com.nth.mike.model.dto.user.UserDTO;
import com.nth.mike.model.response.shared.BasicResponse;
import com.nth.mike.service.ProductDetailService;
import com.nth.mike.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mike/api/cart")
@SessionAttributes("cart") // Khi lưu giỏ hàng vào session
public class CartApiController {

    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @GetMapping("/view")
    public ResponseEntity<?> getCart(HttpSession session) {
        CartOrder cart = (CartOrder) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartOrder();
            session.setAttribute("cart", cart);
        }
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user != null) {
            Account account = userService.findAccountByUserName(user.getUsername());
            UserDTO u = userService.findUserByAccount(account);
            if (u != null) {
                Order order = orderService.findByAccount(account);
                List<OrderDetail> orderDetails = orderDetailService.findByOrder(order);
                if(!orderDetails.isEmpty()){
                    List<CartItem> cartItems = new ArrayList<>();
                    for(OrderDetail od:orderDetails){
                        cartItems.add(CartItemMapper.toCartItem(od));
                    }
                    cart.setCart(cartItems);
                    session.setAttribute("cart", cart);
                }
            }
        }
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(HttpSession session,
            @RequestBody CartItemRequest request) {
        CartOrder cart = (CartOrder) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartOrder();
            session.setAttribute("cart", cart);
        }
        if (request == null) {
            return ResponseEntity.badRequest().body(new BasicResponse(StatusConstant.ERROR, "Invalid product"));
        }
        ProductDetailId pdId = request.getId();
        System.out.println(pdId.toString());
        ProductDetail pd = productDetailService.findById(pdId);
        if (pd == null) {
            return ResponseEntity.badRequest().body(new BasicResponse(StatusConstant.ERROR, "Product not found"));
        }
        Item item = ItemMapper.toItem(pd);
        CartItem cartItem = new CartItem(item, request.getQuantity(), request.getPrice());
        cart.save(cartItem);
        session.setAttribute("cart", cart);

        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user != null) {
            Account account = userService.findAccountByUserName(user.getUsername());
            UserDTO u = userService.findUserByAccount(account);
            if (u != null) {
                Order order = orderService.findByAccount(account);
                if(order==null){
                    order = new Order();
                    order.setAccount(account);
                    order = orderService.save(order);
                }
                ProductDetailId productDetailId = ItemMapper.toProductDetailId(cartItem.getItem());
                OrderDetailId orderDetailId = CartItemMapper.toOrderDetailId(order,productDetailId);

                OrderDetail orderDetail = orderDetailService.findById(orderDetailId);
                if(orderDetail == null){
                    orderDetail = new OrderDetail();
                    orderDetail.setId(orderDetailId);
                    orderDetail.setProductDetail(productDetailService.findById(productDetailId));
                    orderDetail.setOrderPrice(cartItem.getPrice());
                    orderDetail.setQuantity(cartItem.getQuantity());
                    orderDetailService.save(orderDetail);
                }else{
                    Integer oldQuantity = orderDetail.getQuantity() == null ? 0 : orderDetail.getQuantity();
                    orderDetail.setQuantity(oldQuantity + cartItem.getQuantity());
                    orderDetailService.save(orderDetail);
                }
            }
        }
        return ResponseEntity.ok(cart);
    }
}
