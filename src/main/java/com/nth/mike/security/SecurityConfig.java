package com.nth.mike.security;

import com.nth.mike.security.oauth2.CustomOAuth2UserService;
import com.nth.mike.security.oauth2.OAuth2LoginSuccessHandler;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

import static org.springframework.http.HttpMethod.GET;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/mike/api/user/auth/login/**", "/mike/api/user/auth/token/refresh/**",
                        "/mike/api/user/auth/token/accept/**")
                .permitAll()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers(GET, "/mike/api/user/**").hasAnyAuthority("ROLE_USER")
                // .antMatchers("/mike/api/product/**").hasAnyAuthority("ROLE_USER")
                // .antMatchers(GET, "/mike/admin/**").hasAnyAuthority(("ROLE_USER"))
                // .antMatchers("/mike/**").hasAnyRole("USER")
                .anyRequest().permitAll();
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.formLogin()
                .loginPage("/mike/auth/login")
                .defaultSuccessUrl("/mike/home")
                .and()
                .oauth2Login()
                .loginPage("/mike/auth/login")
                .defaultSuccessUrl("/mike/home")
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .successHandler(oAuth2LoginSuccessHandler)
                .and()
                .logout()
                .logoutUrl("/mike/auth/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/mike/auth/login");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/images/**", "/product/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        SimpleUrlLogoutSuccessHandler successHandler = new SimpleUrlLogoutSuccessHandler();
        successHandler.setDefaultTargetUrl("/mike/auth/login?logout");
        return successHandler;
    }

}
