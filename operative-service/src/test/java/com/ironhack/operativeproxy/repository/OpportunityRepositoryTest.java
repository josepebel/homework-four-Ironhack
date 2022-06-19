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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpportunityRepositoryTest {

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private AccountRepository accountRepository;

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
    private Account account4;

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
        opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        opportunity5 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
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
    void findById_ReturnEmpty_OpportunityNotFound() {
        Optional<Opportunity> optionalOpportunity = opportunityRepository.findById(-1);
        assertFalse(optionalOpportunity.isPresent());

        optionalOpportunity = opportunityRepository.findById(0);
        assertFalse(optionalOpportunity.isPresent());
    }

    @Test
    void findById_ReturnOpportunity_OpportunityFound() {
        Optional<Opportunity> optionalOpportunity = opportunityRepository.findById(opportunity1.getId());
        assertTrue(optionalOpportunity.isPresent());
        assertEquals(opportunity1, optionalOpportunity.get());

        optionalOpportunity = opportunityRepository.findById(opportunity2.getId());
        assertTrue(optionalOpportunity.isPresent());
        assertEquals(opportunity2, optionalOpportunity.get());
    }

    @Test
    void findAll_ReturnEmptyList_NoOpportunityInRepository() {
        opportunityRepository.deleteAll();
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        assertEquals(0, opportunityList.size());
    }

    @Test
    void findAll_ReturnOpportunityList_OpportunityInRepository() {
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        assertEquals(6, opportunityList.size());
        assertEquals(opportunity1, opportunityList.get(0));
        assertEquals(opportunity2, opportunityList.get(1));
        assertEquals(opportunity3, opportunityList.get(2));
        assertEquals(opportunity4, opportunityList.get(3));
        assertEquals(opportunity5, opportunityList.get(4));
        assertEquals(opportunity6, opportunityList.get(5));
    }

    @Test
    void updateOpportunityStatus_ChangeStatus_OpportunityExits() {
        Optional<Opportunity> optionalOpportunity = opportunityRepository.findById(opportunity1.getId());
        opportunityRepository.updateOpportunityStatus(optionalOpportunity.get().getId(), Status.CLOSED_LOST);
        optionalOpportunity = opportunityRepository.findById(opportunity1.getId());
        assertEquals(Status.CLOSED_LOST, optionalOpportunity.get().getStatus());

        optionalOpportunity = opportunityRepository.findById(opportunity2.getId());
        opportunityRepository.updateOpportunityStatus(optionalOpportunity.get().getId(), Status.CLOSED_WON);
        optionalOpportunity = opportunityRepository.findById(opportunity2.getId());
        assertEquals(Status.CLOSED_WON, optionalOpportunity.get().getStatus());
    }


    @Test
    void findByUserId_ReturnEmpty_UserNotFound() {
        List<Opportunity> opportunityList = opportunityRepository.findByUserId(99);
        assertEquals(0, opportunityList.size());

    }

    @Test
    void findByUserId_ReturnListOfLeads_UserFound() {
        List<Opportunity> opportunityList1 = opportunityRepository.findByUserId(user.getId());
        assertEquals(6, opportunityList1.size());

    }
}