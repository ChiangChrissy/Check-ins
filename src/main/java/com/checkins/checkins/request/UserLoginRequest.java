package com.checkins.checkins.request;

import javax.validation.constraints.NotEmpty;

public class UserLoginRequest {
    @NotEmpty
    private String Username;
    @NotEmpty
    private String Password;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
