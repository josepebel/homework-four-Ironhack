package com.ironhack.reportingservice.model.person;

import com.ironhack.reportingservice.model.User;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "leader")
@PrimaryKeyJoinColumn(name = "person_id")
public class Lead extends Person {

    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Lead() {
    }

    public Lead(String name, String phoneNumber, String email, String companyName, Date creationDate, Date modificationDate, String userCreation, String userModification, User user) {
        super(name, phoneNumber, email, companyName, creationDate, modificationDate, userCreation, userModification);
        this.user = user;
//        this.id = ++totalLead;
    }

    public Lead(String name, String phoneNumber, String email, String companyName) {
        super(name, phoneNumber, email, companyName);
//        this.id = ++totalLead;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        String id = String.format("%-10s", this.id);
        String name = String.format("%-40s", this.getName());

        return id + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lead lead = (Lead) o;
        return id == lead.id && Objects.equals(user, lead.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user);
    }
}
