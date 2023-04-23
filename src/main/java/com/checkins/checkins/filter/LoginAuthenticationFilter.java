package com.checkins.checkins.filter;

import com.checkins.checkins.entity.EmployeeEntity;
import com.checkins.checkins.repo.EmployeeRepository;
import com.checkins.checkins.request.MyHttpServletRequestWrapper;
import com.checkins.checkins.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@Component
public class LoginAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAuthenticationFilter.class);
    private boolean isPwdMatches;
    private boolean isPhoneMatches;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LOGGER.info(">>>>進到LoginAuthenticationFilter");
        String authHeader = request.getHeader("Authorization");
        LOGGER.info("Header-Authorization：" + authHeader);
        //首次登入，header會帶Authorization:Login
        if (Objects.equals(authHeader, "Login") && !authHeader.startsWith("Bearer ")) {

            // 取得登入時帶的request body
            String requestBody = authenticationService.getRequestBody(request);
            LOGGER.info("request body 內容：" + requestBody);
            // 取得request body裡的登入資訊
            HashMap<String, String> map = authenticationService.getUserFromRequestBody(requestBody);
            String username2 = map.get("Username");
            String password2 = map.get("Password");
            LOGGER.info(">>>>Search data from db...");
            EmployeeEntity employeeEntity = employeeRepository.findByName(username2).orElse(null);
            // check user exist or not
            if (employeeEntity == null) {
                throw new NullPointerException("User not exist!");
            }
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            isPwdMatches = bCryptPasswordEncoder.matches(password2, employeeEntity.getPassword());
            LOGGER.info("Username from DB：" + employeeEntity.getName());
            LOGGER.info("Userpwd from DB：" + employeeEntity.getPassword());
            LOGGER.info("輸入的密碼是否與DB的相符：" + isPwdMatches);
            LOGGER.info("輸入的手機是否與DB的相符：" + isPhoneMatches);

            //判斷輸入的帳密是否跟db一樣
            if (username2.equals(employeeEntity.getName()) && isPwdMatches) {
                LOGGER.info("User and pwd R correct!");
                LOGGER.info("username2:" + username2);
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username2);
                UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken.authenticated(
                        userDetails, null, userDetails.getAuthorities());
                LOGGER.info("authToken.isAuthenticated()：" + authToken.isAuthenticated());
                LOGGER.info("userDetails.getAuthorities()：" + userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
                LOGGER.info("authToken：" + authToken);
                MyHttpServletRequestWrapper wrapper = new MyHttpServletRequestWrapper(request, username2);
                LOGGER.info(">>>>Before filterChain.doFilter__Login");
                filterChain.doFilter(wrapper, response);
//                filterChain.doFilter(request, response);
                LOGGER.info(">>>>After filterChain.doFilter__Login");

            } else {
                throw new NullPointerException("Wrong password.");
            }
        } else if (authHeader == null) {
            LOGGER.info(">>>完全不需要驗證，直接跳過loginFilter");
            filterChain.doFilter(request, response);
            LOGGER.info(">>>執行完filterChain.doFilter 現在在loginFilter");

        } else {
            LOGGER.info(">>>已登入過，要驗證jwt，直接跳過loginFilter");
            LOGGER.info("______________authHeader：" + authHeader);
            filterChain.doFilter(request, response);
            LOGGER.info(">>>執行完filterChain.doFilter 現在在loginFilter");
        }
    }//doFilterInternal

}

