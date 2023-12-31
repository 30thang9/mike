package com.nth.mike.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nth.mike.model.cart.CartOrder;
import com.nth.mike.model.dto.user.UserDTO;
import com.nth.mike.security.response.UserTokenResponse;
import com.nth.mike.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
        private final AuthenticationManager authenticationManager;
        private final UserService userService;

        @Value("${spring.auth.hashToken.secret}")
        private String secret;

        // public CustomAuthenticationFilter(AuthenticationManager
        // authenticationManager) {
        // this.authenticationManager = authenticationManager;
        // }

        // @Override
        // public Authentication attemptAuthentication(HttpServletRequest request,
        // HttpServletResponse response) throws AuthenticationException {
        // String username = request.getParameter("username");
        // String password = request.getParameter("password");
        // log.info("User name is {}",username);
        // log.info("Password is {}",password);
        // UsernamePasswordAuthenticationToken authenticationToken=new
        // UsernamePasswordAuthenticationToken(username,password);
        // return authenticationManager.authenticate(authenticationToken);
        // }

        public CustomAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
                this.authenticationManager = authenticationManager;
                this.userService = userService;
        }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
                        throws AuthenticationException {
                try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        UserAuthRequest userAuthRequest = objectMapper.readValue(request.getInputStream(),
                                        UserAuthRequest.class);

                        String username = userAuthRequest.getUsername();
                        String password = userAuthRequest.getPassword();
                        log.info("User name is {}", username);
                        log.info("Password is {}", password);
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                        username,
                                        password);
                        return authenticationManager.authenticate(authenticationToken);
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
        }

        @Override
        protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                        FilterChain chain,
                        Authentication authentication) throws IOException, ServletException {
                // super.successfulAuthentication(request, response, chain, authResult);
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
                // session.setAttribute("cart", new CartOrder());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),
                                new UserTokenResponse(userDTO, accessToken, refreshToken));
        }

        @Override
        protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                        AuthenticationException failed) throws IOException, ServletException {
                super.unsuccessfulAuthentication(request, response, failed);

                // Ghi lại các hoạt động đăng nhập không thành công (nếu cần)
                log.error("Authentication failed for user: {}", request.getParameter("username"));

                // Gửi thông báo lỗi về cho người dùng (nếu cần)
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Authentication failed. Please check your username and password.");

                // Hoặc chuyển hướng đến trang đăng nhập với thông báo lỗi (nếu cần)
                // response.sendRedirect("/login?error=1");
        }
}
