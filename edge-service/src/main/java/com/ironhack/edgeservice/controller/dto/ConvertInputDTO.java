package com.ironhack.edgeservice.controller.dto;

import com.ironhack.edgeservice.enums.Industry;
import com.ironhack.edgeservice.enums.Product;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ConvertInputDTO {

    @NotNull(message = "Product is required")
    private Product product;
    @NotNull
    @DecimalMin(value = "0", message = "Quantity must be above 0")
    private int quantity;
    @NotNull(message="Industry required")
    private Industry industry;
    @NotNull
    @DecimalMin(value = "0", message = "Number of employees must be above 0")
    private int employeeCount;
    @NotEmpty(message = "City name is required")
    private String city;
    @NotEmpty(message = "Country name is required")
    private String country;

    public ConvertInputDTO() {
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

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
