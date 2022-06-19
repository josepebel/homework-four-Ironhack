package com.ironhack.reportingservice.model;

import com.ironhack.reportingservice.enums.Industry;
import com.ironhack.reportingservice.model.person.Contact;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private Industry industry;
    private int employeeCount;
    private String city;
    private String country;
    private Date creationDate;
    private Date modificationDate;
    private String userCreation;
    private String userModification;
    @OneToMany(mappedBy = "account")
    private List<Contact> contactList;
    @OneToMany(mappedBy = "account")
    private List<Opportunity> opportunityList;

    public Account() {
    }

    public Account(Industry industry, int employeeCount, String city, String country, Date creationDate, Date modificationDate, String userCreation, String userModification, List<Contact> contactList, List<Opportunity> opportunityList) {
        this.industry = industry;
        this.employeeCount = employeeCount;
        this.city = city;
        this.country = country;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.userCreation = userCreation;
        this.userModification = userModification;
        this.contactList = contactList;
        this.opportunityList = opportunityList;
    }

    public Account(Industry industry, int employeeCount, String city, String country) {
        this.industry = industry;
        this.employeeCount = employeeCount;
        this.city = city;
        this.country = country;
        this.contactList = new ArrayList<>();
        this.opportunityList = new ArrayList<>();
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getUserCreation() {
        return userCreation;
    }

    public void setUserCreation(String userCreation) {
        this.userCreation = userCreation;
    }

    public String getUserModification() {
        return userModification;
    }

    public void setUserModification(String userModification) {
        this.userModification = userModification;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public List<Opportunity> getOpportunityList() {
        return opportunityList;
    }

    public void setOpportunityList(List<Opportunity> opportunityList) {
        this.opportunityList = opportunityList;
    }

}
