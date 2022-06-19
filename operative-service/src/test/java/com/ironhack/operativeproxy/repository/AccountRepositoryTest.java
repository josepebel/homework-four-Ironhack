package com.ironhack.operativeproxy.repository;

import com.ironhack.operativeproxy.enums.Industry;
import com.ironhack.operativeproxy.enums.Product;
import com.ironhack.operativeproxy.enums.Status;
import com.ironhack.operativeproxy.enums.UserType;
import com.ironhack.operativeproxy.model.Account;
import com.ironhack.operativeproxy.model.Opportunity;
import com.ironhack.operativeproxy.model.User;
import com.ironhack.operativeproxy.model.person.Contact;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    private Opportunity opportunity1;
    private Opportunity opportunity2;
    private Opportunity opportunity3;
    private Opportunity opportunity4;
    private Opportunity opportunity5;
    private Opportunity opportunity6;

    private Account account1;
    private Account account2;
    private Account account3;


    private User user;
    private Contact contact;

    @BeforeEach
    void setUp() {
        account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        opportunity2 = new Opportunity(Product.FLATBED, 90, Status.OPEN, user, contact, account1);
        opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        opportunity4 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_LOST, user, contact, account2);
        opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_WON, user, contact, account2);
        opportunity6 = new Opportunity(Product.FLATBED, 5, Status.CLOSED_WON, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
    }

    @AfterEach
    void tearDown() {
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findById_ReturnEmpty_AccountNotFound() {
        Optional<Account> optionalAccount = accountRepository.findById(-1);
        assertFalse(optionalAccount.isPresent());

        optionalAccount = accountRepository.findById(0);
        assertFalse(optionalAccount.isPresent());
    }

    @Test
    void findById_ReturnAccount_OpportunityFound() {
        Optional<Account> optionalAccount = accountRepository.findById(account1.getId());
        assertTrue(optionalAccount.isPresent());
        assertEquals(200, optionalAccount.get().getEmployeeCount());

        optionalAccount = accountRepository.findById(account2.getId());
        assertTrue(optionalAccount.isPresent());
        assertEquals(60, optionalAccount.get().getEmployeeCount());
    }


}