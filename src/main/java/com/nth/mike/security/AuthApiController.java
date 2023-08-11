package com.nth.mike.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nth.mike.constant.StatusConstant;
import com.nth.mike.entity.Account;
import com.nth.mike.entity.Order;
import com.nth.mike.entity.OrderDetail;
import com.nth.mike.model.cart.CartItem;
import com.nth.mike.model.cart.CartItemMapper;
import com.nth.mike.model.cart.CartOrder;
import com.nth.mike.model.dto.user.UserDTO;
import com.nth.mike.security.response.BasicAuthResponse;
import com.nth.mike.security.response.UserTokenResponse;
import com.nth.mike.service.OrderDetailService;
import com.nth.mike.service.OrderService;
import com.nth.mike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mike/api/user/auth")
public class AuthApiController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response,
            @RequestBody UserAuthRequest userAuthRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userAuthRequest.getUsername(),
                            userAuthRequest.getPassword()));

            User user = (User) authentication.getPrincipal();
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            String accessToken = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles",
                            user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.toList()))
                    .sign(algorithm);

            String refreshToken = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                    .withIssuer(request.getRequestURL().toString())
                    .sign(algorithm);

            UserDTO userDTO = userService.findUserByAccount(userService.findAccountByUserName(user.getUsername()));

            HttpSession session = request.getSession();
            session.setAttribute("user", userDTO);
            Account account = userService.findAccountByUserName(userDTO.getUsername());
            Order order = orderService.findByAccount(account);
            List<OrderDetail> orderDetails = orderDetailService.findByOrder(order);
            CartOrder cart = new CartOrder();
            List<CartItem> cartItems = new ArrayList<>();
            if (!orderDetails.isEmpty()) {
                for (OrderDetail od : orderDetails) {
                    cartItems.add(CartItemMapper.toCartItem(od));
                }
            }
            cart.setCart(cartItems);
            session.setAttribute("cart", cart);
            return ResponseEntity.ok(new UserTokenResponse(userDTO, accessToken, refreshToken));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new BasicAuthResponse(StatusConstant.ERROR,
                            "Authentication failed. Please check your username and password."));
        }
    }

    // Other methods for token refresh, etc.

}
