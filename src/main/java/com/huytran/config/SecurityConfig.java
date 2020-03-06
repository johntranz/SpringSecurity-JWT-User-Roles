package com.huytran.config;

import com.huytran.handler.CustomAccessDeniedHandler;
import com.huytran.jwt.JwtAuthenticationFilter;
import com.huytran.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configurable
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService)
                .passwordEncoder(passwordEncoder());
    }
    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/v1/accounts/login").permitAll()
                .antMatchers(HttpMethod.GET,"/v1/contracts/**").access("hasRole('ROLE_ADMIN')or hasRole('ROLE_NORMAL')")
                .antMatchers(HttpMethod.POST,"/v1/contracts/").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.PUT,"/v1/contracts/").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.GET,"/v1/customers/**").access("hasRole('ROLE_ADMIN')or hasRole('ROLE_NORMAL')")
                .antMatchers(HttpMethod.POST,"/v1/customers/").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.PUT,"/v1/customers/").access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .invalidSessionUrl("/invalidSession.html");
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
    }


}
