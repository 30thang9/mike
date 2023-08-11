package com.nth.mike.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nth.mike.filter.RouteFilter;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<RouteFilter> customRouteFilter(RouteFilter routeFilter) {
        FilterRegistrationBean<RouteFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(routeFilter);
        registrationBean.addUrlPatterns("/*"); // Áp dụng Filter cho tất cả các URL
        // registrationBean.addInitParameter("excludedUrls", "/mike/auth/login");
        return registrationBean;
    }
}
