package com.nth.mike.security.oauth2;

import com.nth.mike.entity.*;
import com.nth.mike.model.cart.CartItem;
import com.nth.mike.model.cart.CartItemMapper;
import com.nth.mike.model.cart.CartOrder;
import com.nth.mike.model.dto.user.UserDTO;
import com.nth.mike.service.OrderDetailService;
import com.nth.mike.service.OrderService;
import com.nth.mike.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        HttpSession session = request.getSession();
        UserDTO user = UserDTO.builder()
                .username(customOAuth2User.getEmail())
                .fullName(customOAuth2User.getName())
                .build();
        session.setAttribute("user", user);
        Account account = userService.findAccountByUserName(user.getUsername());

        if (account != null) {
            List<Role> roles = userService.findRoleByAccount(account);
            List<RoleName> roleNames = roles.stream()
                    .filter(role -> !RoleName.ROLE_USER.equals(role.getName()))
                    .map(Role::getName)
                    .collect(Collectors.toList());

            if (!roleNames.isEmpty()) {
                response.sendRedirect("/mike/admin/home");
                return;
            }else{
                Order order = orderService.findByCustomer(account);
                if (order != null) {
                    CartOrder cart = new CartOrder();
                    List<OrderDetail> orderDetails = orderDetailService.findByOrder(order);
                    List<CartItem> cartItems = new ArrayList<>();
                    if (!orderDetails.isEmpty()) {
                        for (OrderDetail od : orderDetails) {
                            cartItems.add(CartItemMapper.toCartItem(od));
                        }
                    }
                    cart.setId(order.getId());
                    cart.setCart(cartItems);
                    session.setAttribute("cart", cart);
                }
                response.sendRedirect("/mike/home");
                return;
            }
        }
        response.sendRedirect("/mike/home");
        return;
    }
}
