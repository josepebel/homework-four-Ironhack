package com.ironhack.operativeproxy.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SalesInputDTO {
    @NotEmpty(message="Name is required")
    private String name;
    @NotEmpty(message="Password is required")
    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;

    public SalesInputDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
