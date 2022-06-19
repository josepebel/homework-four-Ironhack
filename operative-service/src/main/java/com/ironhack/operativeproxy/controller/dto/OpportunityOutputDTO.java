package com.ironhack.operativeproxy.controller.dto;

import com.ironhack.operativeproxy.enums.Product;
import com.ironhack.operativeproxy.enums.Status;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class OpportunityOutputDTO {

    private int id;
    @Enumerated(EnumType.STRING)
    private Product product;
    private int quantity;
    private String name;
    private String phoneNumber;
    private String email;
    private String companyName;
    @Enumerated(EnumType.STRING)
    private Status status;

    public OpportunityOutputDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
