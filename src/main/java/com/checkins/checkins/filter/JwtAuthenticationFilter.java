package com.checkins.checkins.filter;

import com.checkins.checkins.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        LOGGER.info(">>>>進到JwtAuthenticationFilter");
        final String authHeader = request.getHeader("Authorization");
        LOGGER.info(">>>>authHeader："+authHeader);
        final String jwt;
        final String jwtUserName;

        //首次登入或本來就不需驗證，無須驗證token
        if (authHeader==null||!authHeader.startsWith("Bearer ")) {
            LOGGER.info("判斷第一次登入或不須驗證，不須跑jwtFilter的流程：filterChain.doFilter(request, response)");
            filterChain.doFilter(request, response);

        } else {
            //需要驗證token的時候
            jwt = authHeader.substring(7);
            jwtUserName = jwtService.extractUsername(jwt);
            LOGGER.info(">>>>>>>jwtUserName = jwtService.extractUsername(jwt)：" + jwtUserName);
            if (jwtUserName != null) {
                LOGGER.info(">>>進行token驗證");
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtUserName);
                //設定權限的地方，目前設定 MANAGER 才可以通過驗證。
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    LOGGER.info("userDetails.getAuthorities()：" + userDetails.getAuthorities());
                    //如果能在db找到使用者，jwt驗證也通過，那就update security context holder.
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    LOGGER.info(">>>Before filterChain.doFilter()__jwt");
                    filterChain.doFilter(request, response);
                    LOGGER.info(">>>After filterChain.doFilter()__jwt");
                }
            }
        }//else
    }//doFilterInternal
}
