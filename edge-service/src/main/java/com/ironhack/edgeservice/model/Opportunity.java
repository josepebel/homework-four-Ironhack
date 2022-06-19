package com.ironhack.edgeservice.model;

import com.ironhack.edgeservice.enums.Product;
import com.ironhack.edgeservice.enums.Status;
import com.ironhack.edgeservice.model.person.Contact;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@DynamicUpdate
public class Opportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private Product product;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "contact_id")
    private Contact decisionMaker;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private Date creationDate;
    private Date modificationDate;
    private String userCreation;
    private String userModification;

    public Opportunity() {
    }

    public Opportunity(Product product, int quantity, Status status, User user, Contact decisionMaker, Account account, Date creationDate, Date modificationDate, String userCreation, String userModification) {
        this.product = product;
        this.quantity = quantity;
        this.status = status;
        this.user = user;
        this.decisionMaker = decisionMaker;
        this.account = account;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.userCreation = userCreation;
        this.userModification = userModification;
    }

    public Opportunity(Product product, int quantity, Status status, User user, Contact decisionMaker, Account account) {
        this.product = product;
        this.quantity = quantity;
        this.status = status;
        this.user = user;
        this.decisionMaker = decisionMaker;
        this.account = account;
    }

    public Opportunity(Product product, int quantity, Contact decisionMaker, Status status) {
//        this.id = totalOpportunity++;
        this.product = product;
        this.quantity = quantity;
        this.decisionMaker = decisionMaker;
        this.status = status;
    }

    public int getId() {
        return id;
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

    public Contact getDecisionMaker() {
        return decisionMaker;
    }

    public void setDecisionMaker(Contact decisionMaker) {
        this.decisionMaker = decisionMaker;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    @Override
    public String toString() {

        String idToShow = String.format("%-10s", id);
        String productToShow = String.format("%-15s", product);
        String quantityToShow = String.format("%-10s", quantity);
        String decisionMakerToShow = String.format("%-40s", decisionMaker);
        String statusToShow = String.format("%-10s", status);

        return idToShow + productToShow + quantityToShow + decisionMakerToShow + statusToShow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Opportunity that = (Opportunity) o;
        return quantity == that.quantity && product == that.product && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity, status, user, decisionMaker, account);
    }

    /**
     * method to change Opportunity status
     *
     * @param status new status
     */
    public void changeOpportunityStatus(Status status) {
        if (this.getStatus() != status) {
            this.setStatus(status);
        }
    }
}
