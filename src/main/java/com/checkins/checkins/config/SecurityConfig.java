package com.checkins.checkins.config;

import com.checkins.checkins.filter.JwtAuthenticationFilter;
import com.checkins.checkins.filter.LoginAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;
    @Autowired
    private LoginAuthenticationFilter loginAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/employee/createEmployee").hasAuthority("ADMIN")
                .antMatchers("/employee/all").hasAuthority("MANAGER")
                .antMatchers("/employee/auth/**").authenticated()//要驗證
                .and()
                //每次打api我需要他都經過驗證，所以要設定她不要儲存任何驗證狀態或session狀態，以下設定
                .sessionManagement()
                //設定我們的創建session的policy是什麼，以下
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//無狀態的session政策，不使用HTTPSession
                .and()
                .csrf().disable()
//                在call這個UsernamePasswordAuthenticationFilter之前先call JwtAuthenticationFilter
                .addFilterBefore(loginAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }//securityFilterChain
}