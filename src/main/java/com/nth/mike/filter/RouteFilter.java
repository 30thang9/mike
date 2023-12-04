package com.nth.mike.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nth.mike.entity.Account;
import com.nth.mike.entity.Role;
import com.nth.mike.entity.RoleName;
import com.nth.mike.model.dto.user.UserDTO;
import com.nth.mike.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebFilter
@Order(1)
@Component
public class RouteFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Autowired
    public RouteFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestUri = request.getRequestURI();

        // if (requestUri.contains(";")) {
        // String newRequestUri = requestUri.replaceAll(";", "");
        // response.sendRedirect(newRequestUri);
        // return;
        // }

        if (requestUri.startsWith("/mike/admin")) {
            // HttpSession session = request.getSession();
            // UserDTO user = (UserDTO) session.getAttribute("user");
            // if (user == null) {
            // response.sendRedirect("/mike/error/unauthorized");
            // return;
            // }
            // Account account = userService.findAccountByUserName(user.getUsername());
            // if (account == null) {
            // response.sendRedirect("/mike/error/unauthorized");
            // return;
            // }
            // UserDTO u = userService.findUserByAccount(account);
            // if (u != null) {
            // List<Role> roles = u.getRoles();
            // List<RoleName> roleNames = roles.stream()
            // .filter(role -> !RoleName.ROLE_USER.equals(role.getName()))
            // .map(Role::getName)
            // .collect(Collectors.toList());
            // if (roleNames.isEmpty()) {
            // response.sendRedirect("/mike/error/unauthorized");
            // return;
            //
            // }
            // } else {
            // response.sendRedirect("/mike/error/unauthorized");
            // return;
            // }
        } else if (requestUri.startsWith("/mike/user-profile")) {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect("/mike/error/unauthorized");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
