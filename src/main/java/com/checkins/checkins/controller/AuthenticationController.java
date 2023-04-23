package com.checkins.checkins.controller;

import com.checkins.checkins.request.UserLoginRequest;
import com.checkins.checkins.response.AuthenticationResponse;
import com.checkins.checkins.service.AuthenticationService;
import com.checkins.checkins.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/employee/auth")
public class AuthenticationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(HttpServletRequest request){
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        String name = request.getParameter("username2");
        userLoginRequest.setUsername(name);
        AuthenticationResponse re = authenticationService.login(userLoginRequest);

        return ResponseEntity.ok(re);
    }//login
}
