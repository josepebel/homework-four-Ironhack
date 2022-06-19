package com.ironhack.edgeservice.model;

import com.ironhack.edgeservice.enums.UserType;
import com.ironhack.edgeservice.model.person.Contact;
import com.ironhack.edgeservice.model.person.Lead;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    private String passwordAccess;
    private String status;

    private Date creationDate;
    private Date modificationDate;
    private String userCreation;
    private String userModification;

    @OneToMany(mappedBy = "user")
    private List<Contact> contactList;

    @OneToMany(mappedBy = "user")
    private List<Lead> leadList;

    @OneToMany(mappedBy = "user")
    private List<Opportunity> opportunityList;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Role> roles;

    public User() {
    }

    public User(String name, UserType userType, String passwordAccess, String status, Date creationDate, Date modificationDate, String userCreation, String userModification) {
        this.name = name;
        this.userType = userType;
        this.passwordAccess = passwordAccess;
        this.status = status;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.userCreation = userCreation;
        this.userModification = userModification;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPasswordAccess() {
        return passwordAccess;
    }

    public void setPasswordAccess(String passwordAccess) {
        this.passwordAccess = passwordAccess;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<Lead> getLeadList() {
        return leadList;
    }

    public void setLeadList(List<Lead> leadList) {
        this.leadList = leadList;
    }

    public List<Opportunity> getOpportunityList() {
        return opportunityList;
    }

    public void setOpportunityList(List<Opportunity> opportunityList) {
        this.opportunityList = opportunityList;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && userType == user.userType && Objects.equals(passwordAccess, user.passwordAccess) && Objects.equals(status, user.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, userType, passwordAccess, status);
    }
}
