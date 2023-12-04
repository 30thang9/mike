package com.nth.mike.controller.api;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.nth.mike.entity.*;
import com.nth.mike.model.cart.*;
import com.nth.mike.model.request.checkout.AddressCheckoutRequest;
import com.nth.mike.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nth.mike.constant.StatusConstant;
import com.nth.mike.model.dto.checkout.AddressCheckoutRequestMapper;
import com.nth.mike.model.dto.checkout.AddressCheckoutSession;
import com.nth.mike.model.dto.user.UserDTO;
import com.nth.mike.model.response.shared.BasicResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private DeliveryService deliveryService;

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
                Order order = orderService.findByCustomer(account);
                List<OrderDetail> orderDetails = orderDetailService.findByOrder(order);
                if (!orderDetails.isEmpty()) {
                    List<CartItem> cartItems = new ArrayList<>();
                    for (OrderDetail od : orderDetails) {
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
                Order order = orderService.findByCustomer(account);
                if (order == null) {
                    order = new Order();
                    order.setCustomer(account);
                    order = orderService.save(order);
                    cart.setId(order.getId());
                    session.setAttribute("cart", cart);
                }
                ProductDetailId productDetailId = ItemMapper.toProductDetailId(cartItem.getItem());
                OrderDetailId orderDetailId = CartItemMapper.toOrderDetailId(order, productDetailId);

                OrderDetail orderDetail = orderDetailService.findById(orderDetailId);
                if (orderDetail == null) {
                    orderDetail = new OrderDetail();
                    orderDetail.setId(orderDetailId);
                    orderDetail.setProductDetail(productDetailService.findById(productDetailId));
                    orderDetail.setPaymentPrice(cartItem.getPrice());
                    orderDetail.setQuantity(cartItem.getQuantity());
                    orderDetailService.save(orderDetail);
                } else {
                    Integer oldQuantity = orderDetail.getQuantity() == null ? 0 : orderDetail.getQuantity();
                    orderDetail.setQuantity(oldQuantity + cartItem.getQuantity());
                    orderDetailService.save(orderDetail);
                }
            }
        }
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCart(HttpSession session, @RequestBody CartItemRequest request) {
        CartOrder cart = (CartOrder) session.getAttribute("cart");
        if (cart == null) {
            return ResponseEntity.badRequest().body(new BasicResponse(StatusConstant.ERROR, "Cart is null"));
        }
        if (request == null) {
            return ResponseEntity.badRequest().body(new BasicResponse(StatusConstant.ERROR, "Invalid product"));
        }
        ProductDetailId pdId = request.getId();
        ProductDetail pd = productDetailService.findById(pdId);
        if (pd == null) {
            return ResponseEntity.badRequest().body(new BasicResponse(StatusConstant.ERROR, "Product not found"));
        }
        Item item = ItemMapper.toItem(pd);
        CartItem cartItem = new CartItem(item, request.getQuantity(), request.getPrice());
        cart.update(cartItem);
        session.setAttribute("cart", cart);

        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user != null) {
            Account account = userService.findAccountByUserName(user.getUsername());
            UserDTO u = userService.findUserByAccount(account);
            if (u != null) {
                Order order = orderService.findByCustomer(account);
                if (order != null) {
                    ProductDetailId productDetailId = ItemMapper.toProductDetailId(cartItem.getItem());
                    OrderDetailId orderDetailId = CartItemMapper.toOrderDetailId(order, productDetailId);

                    OrderDetail orderDetail = orderDetailService.findById(orderDetailId);
                    if (orderDetail != null) {
                        orderDetail.setQuantity(cartItem.getQuantity());
                        orderDetailService.save(orderDetail);
                    }
                }
            }
        }
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteItemCart(HttpSession session, @RequestBody ProductDetailId request) {
        CartOrder cart = (CartOrder) session.getAttribute("cart");
        if (cart == null) {
            return ResponseEntity.badRequest().body(new BasicResponse(StatusConstant.ERROR, "Cart is null"));
        }
        if (request == null) {
            return ResponseEntity.badRequest().body(new BasicResponse(StatusConstant.ERROR, "Invalid product"));
        }

        CartItem cartItem = cart.getCartItemById(request);
        if (cartItem == null) {
            return ResponseEntity.badRequest().body(new BasicResponse(StatusConstant.ERROR, "Product not found"));
        }
        ProductDetail pd = productDetailService.findById(request);
        if (pd == null) {
            return ResponseEntity.badRequest().body(new BasicResponse(StatusConstant.ERROR, "Product not found"));
        }
        cart.delete(request);
        session.setAttribute("cart", cart);

        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user != null) {
            Account account = userService.findAccountByUserName(user.getUsername());
            UserDTO u = userService.findUserByAccount(account);
            if (u != null) {
                Order order = orderService.findByCustomer(account);
                if (order != null) {
                    ProductDetailId productDetailId = ItemMapper.toProductDetailId(cartItem.getItem());
                    OrderDetailId orderDetailId = CartItemMapper.toOrderDetailId(order, productDetailId);

                    OrderDetail orderDetail = orderDetailService.findById(orderDetailId);
                    if (orderDetail != null) {
                        orderDetailService.deleteById(orderDetailId);
                    }
                }
            }
        }
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/invalid-cart")
    public ResponseEntity<?> checkValidCart(HttpSession session) {
        CartOrder cart = (CartOrder) session.getAttribute("cart");
        if (cart == null) {
            return ResponseEntity.badRequest().body(new BasicResponse(StatusConstant.ERROR, "Cart is null"));
        }
        if (!cart.isValidCartItem()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new BasicResponse(StatusConstant.ERROR, "Invalid item"));
        }
        return ResponseEntity.ok(new BasicResponse(StatusConstant.SUCCESS, "Item is valid"));
    }

    @PostMapping("/checkout-address")
    public ResponseEntity<?> addAddress(HttpSession session, @RequestBody @Valid AddressCheckoutRequest address) {
        CartOrder cart = (CartOrder) session.getAttribute("cart");
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new BasicResponse(StatusConstant.ERROR, "Cart is null"));
        }
        AddressCheckoutSession addressSec = AddressCheckoutRequestMapper.toAddressId(address);
        session.setAttribute("addressSec", addressSec);
        Order order = new Order();
        if (cart.getId() == null) {
            UserDTO user = (UserDTO) session.getAttribute("user");
            if (user != null) {
                Account account = userService.findAccountByUserName(user.getUsername());
                if (account != null) {
                    order.setCustomer(account);
                }
            }
            order = orderService.save(order);
            cart.setId(order.getId());
            session.setAttribute("cart", cart);

            for (CartItem cartItem : cart.getCart()) {
                ProductDetail pd = productDetailService.findById(cartItem.getItem().getId());
                orderDetailService.save(CartItemMapper.toOrderDetail(order, cartItem, pd));
            }
        } else {
            order = orderService.findById(cart.getId());
            if (order == null) {
                order = new Order();
                UserDTO user = (UserDTO) session.getAttribute("user");
                if (user != null) {
                    Account account = userService.findAccountByUserName(user.getUsername());
                    if (account != null) {
                        order.setCustomer(account);
                    }
                }
                order = orderService.save(order);
                cart.setId(order.getId());
                session.setAttribute("cart", cart);

                for (CartItem cartItem : cart.getCart()) {
                    ProductDetail pd = productDetailService.findById(cartItem.getItem().getId());
                    orderDetailService.save(CartItemMapper.toOrderDetail(order, cartItem, pd));
                }
            }
        }

        order = AddressCheckoutRequestMapper.toOrder(order, address);
        try {
            orderService.save(order);
            return ResponseEntity.ok(new BasicResponse(StatusConstant.SUCCESS, "Add address is successfully!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BasicResponse(StatusConstant.ERROR, "Add address is failed!"));
        }
    }
    @PostMapping("/checkout-payment")
    public ResponseEntity<?> addPayment(HttpSession session, @RequestBody Map<String,String> request) {
        CartOrder cart = (CartOrder) session.getAttribute("cart");
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new BasicResponse(StatusConstant.ERROR, "Cart is null"));
        }
        String payment = request.get("payment_method");
        Long delivery_id = Long.valueOf(request.get("delivery_id"));
        System.out.println(payment);
        Order order =  orderService.findById(cart.getId());
        if (order == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new BasicResponse(StatusConstant.ERROR, "Order null"));
        }

        order.setDelivery(deliveryService.findById(delivery_id));
        order.setPaymentMethod(PaymentMethod.valueOf(payment));
        orderService.save(order);
        try {
            orderService.save(order);
            return ResponseEntity.ok(new BasicResponse(StatusConstant.SUCCESS, "Add pay is successfully!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BasicResponse(StatusConstant.ERROR, "Add address is failed!"));
        }
    }

    @PostMapping("/checkout-final")
    public ResponseEntity<?> addPaymentFinal(HttpSession session, @RequestBody Map<String,String> request) {
        CartOrder cart = (CartOrder) session.getAttribute("cart");
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new BasicResponse(StatusConstant.ERROR, "Cart is null"));
        }

        Order order =  orderService.findById(cart.getId());
        if (order == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new BasicResponse(StatusConstant.ERROR, "Order null"));
        }
        String msg = request.get("mes");
        if(!msg.equals("order")){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new BasicResponse(StatusConstant.ERROR, "False"));
        }
        List<OrderDetail> orderDetails = orderDetailService.findByOrder(order);
        boolean isValid = true;
        for(OrderDetail o: orderDetails){
            ProductDetailId pd  = new ProductDetailId();
            pd.setSizeId(o.getId().getSizeId());
            pd.setColorId(o.getId().getColorId());
            pd.setProductId(o.getId().getProductId());
            ProductDetail pdh = productDetailService.findById(pd);
            if(o.getQuantity()>pdh.getQuantity()){
                isValid = false;
            }
        }
        if(!isValid){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BasicResponse(StatusConstant.ERROR, "Product not pay"));
        }
        try {
            order.setOrderStatus(OrderStatus.PENDING);
            order = orderService.save(order);
            List<OrderDetail> orderDe= orderDetailService.findByOrder(order);
            for(OrderDetail o: orderDe){
                ProductDetailId pd  = new ProductDetailId();
                pd.setSizeId(o.getId().getSizeId());
                pd.setColorId(o.getId().getColorId());
                pd.setProductId(o.getId().getProductId());
                ProductDetail pdh = productDetailService.findById(pd);
                int quan = pdh.getQuantity();
                pdh.setQuantity(quan - o.getQuantity());
                productDetailService.save(pdh);
            }
            cart.deleteAll();
            cart.setId(null);
            session.setAttribute("cart",cart);
            AddressCheckoutSession addressSec = new AddressCheckoutSession();
            session.setAttribute("addressSec", addressSec);
            return ResponseEntity.ok(new BasicResponse(StatusConstant.SUCCESS, "Add pay is successfully!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BasicResponse(StatusConstant.ERROR, "Add address is failed!"));
        }
    }

}
