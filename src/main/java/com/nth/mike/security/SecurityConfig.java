package com.nth.mike.security;

import com.nth.mike.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), userService);
        customAuthenticationFilter.setFilterProcessesUrl("/mike/api/user/auth/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/mike/api/user/auth/login/**", "/mike/api/user/auth/token/refresh/**","/mike/api/user/auth/token/accept/**").permitAll()
                .antMatchers(GET, "/mike/api/user/**").hasAnyAuthority("ROLE_USER")
//                .antMatchers("/mike/api/product/**").hasAnyAuthority("ROLE_USER")
//                .antMatchers("/mike/admin/**").hasAnyAuthority(("ROLE_USER"))
//                .antMatchers("/mike/**").hasAnyRole("USER")
                .anyRequest().permitAll();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.formLogin()
                .loginPage("/mike/auth/login")
                .defaultSuccessUrl("/mike/admin/home") // Điều hướng đến trang "/dashboard" sau khi đăng nhập thành công
                .and()
                .logout()
                .logoutSuccessUrl("/mike/auth/login")
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/mike/auth/login");
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

