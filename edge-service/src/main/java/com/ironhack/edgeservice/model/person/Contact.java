package com.ironhack.edgeservice.model.person;

import com.ironhack.edgeservice.model.Account;
import com.ironhack.edgeservice.model.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name = "person_id")
public class Contact extends Person {

    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Contact() {
    }

    public Contact(String name, String phoneNumber, String email, String companyName, Date creationDate, Date modificationDate, String userCreation, String userModification, Account account) {
        super(name, phoneNumber, email, companyName, creationDate, modificationDate, userCreation, userModification);
        this.account = account;
        this.user = user;
    }

    public Contact(String name, String phoneNumber, String email, String companyName) {
        super(name, phoneNumber, email, companyName);
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String toString() {

        String name = String.format("%-30s", this.getName());
        String phoneNumber = String.format("%-24s", this.getPhoneNumber());
        String email = String.format("%-40s", this.getEmail());
        String companyName = String.format("%-40s", this.getCompanyName());

        return name + phoneNumber + email + companyName;

    }
}
