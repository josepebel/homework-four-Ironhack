package com.ironhack.reportingservice.controller.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.reportingservice.enums.Industry;
import com.ironhack.reportingservice.enums.Product;
import com.ironhack.reportingservice.enums.Status;
import com.ironhack.reportingservice.enums.UserType;
import com.ironhack.reportingservice.model.Account;
import com.ironhack.reportingservice.model.Opportunity;
import com.ironhack.reportingservice.model.User;
import com.ironhack.reportingservice.model.person.Contact;
import com.ironhack.reportingservice.model.person.Lead;
import com.ironhack.reportingservice.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ReportControllerImplTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LeadRepository leadRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private User userOne;
    private Contact contact;
    private Opportunity opportunity;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @AfterEach
    void tearDown() {
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        accountRepository.deleteAll();
        leadRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void reportMeanOppsAccount_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/mean-opps-account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities in any Account"));
    }

    @Test
    void reportMeanOppsAccount_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.OPEN, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));


        MvcResult mvcResult = mockMvc.perform(get("/report/mean-opps-account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Mean Opportunities per Account is: 2"));
    }

    @Test
    void reportMaxOppsAccount_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/max-opps-account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities in any Account"));
    }

    @Test
    void reportMaxOppsAccount_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.OPEN, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));


        MvcResult mvcResult = mockMvc.perform(get("/report/max-opps-account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("Max Opportunities per Account is: 3", mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void reportMinOppsAccount_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/min-opps-account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities in any Account"));
    }

    @Test
    void reportMinOppsAccount_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.OPEN, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));


        MvcResult mvcResult = mockMvc.perform(get("/report/min-opps-account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("Min Opportunities per Account is: 1", mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void reportMedianOppsAccount_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/median-opps-account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities in any Account"));
    }

    @Test
    void reportMedianOppsAccount_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.OPEN, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/median-opps-account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("Median Opportunities per Account is: 1.5000", mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void reportClosedWonByCity_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-won-by-city")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities close-won in system"));
    }

    @Test
    void reportClosedWonByCity_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_WON, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_WON, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-won-by-city")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Madrid"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("1"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Cartagena"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("1"));

    }

    @Test
    void reportCloseLostByCity_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-lost-by-city")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities close-lost in system"));
    }

    @Test
    void reportCloseLostByCity_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_LOST, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_LOST, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-lost-by-city")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Madrid"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("1"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Cartagena"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("1"));

    }

    @Test
    void reportOpenByCity_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/open-by-city")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities open in system"));
    }

    @Test
    void reportOpenByCity_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_WON, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_WON, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/open-by-city")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Madrid"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("2"));
    }

    @Test
    void reportClosedWonBySalesRep_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-won-by-sales")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not leads with close-won opportunities in system"));
    }

    @Test
    void reportClosedWonBySalesRep_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        User user2 = new User("user two", UserType.SALESREPRESENTATIVE, "12344543", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);
        userRepository.save(user2);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account2);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_WON, user2, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_WON, user2, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-won-by-sales")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("user two"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("2"));

    }

    @Test
    void reportCloseLostBySalesRep_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-lost-by-sales")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not leads with close-lost opportunities in system"));
    }

    @Test
    void reportCloseLostBySalesRep_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        User user2 = new User("user two", UserType.SALESREPRESENTATIVE, "12344543", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);
        userRepository.save(user2);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account2);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_LOST, user2, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_LOST, user2, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-lost-by-sales")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("user two"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("2"));

    }

    @Test
    void reportOpenBySalesRep_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/open-by-sales")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not leads with open opportunities in system"));
    }

    @Test
    void reportOpenBySalesRep_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        User user2 = new User("user two", UserType.SALESREPRESENTATIVE, "12344543", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);
        userRepository.save(user2);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account2);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user2, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_WON, user2, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user2, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_WON, user2, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user2, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/open-by-sales")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("user two"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("3"));
    }

    @Test
    void reportClosedWonByProduct_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-won-by-product")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities close-won in system"));
    }

    @Test
    void reportClosedWonByProduct_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_WON, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_WON, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-won-by-product")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("FLATBED"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("2"));

    }

    @Test
    void reportCloseLostByProduct_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-lost-by-product")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities close-lost in system"));
    }

    @Test
    void reportCloseLostByProduct_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_LOST, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_LOST, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-lost-by-product")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("FLATBED"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("2"));

    }

    @Test
    void reportOpenByProduct_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/open-by-product")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities open in system"));
    }

    @Test
    void reportOpenByProduct_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_WON, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_WON, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/open-by-product")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("FLATBED"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("4"));
    }

    @Test
    void reportClosedWonByCountry_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-won-by-country")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities close-won in system"));
    }

    @Test
    void reportClosedWonByCountry_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_WON, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_WON, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-won-by-country")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Spain"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("2"));

    }

    @Test
    void reportCloseLostByCountry_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-lost-by-country")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities close-lost in system"));
    }

    @Test
    void reportCloseLostByCountry_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_LOST, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_LOST, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-lost-by-country")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Spain"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("2"));

    }

    @Test
    void reportOpenByCountry_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/open-by-country")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities open in system"));
    }

    @Test
    void reportOpenByCountry_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_WON, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_WON, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/open-by-country")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Spain"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("4"));
    }

    @Test
    void reportClosedWonByIndustry_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-won-by-industry")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities close-won in system"));
    }

    @Test
    void reportClosedWonByIndustry_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_WON, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_WON, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-won-by-industry")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("OTHER"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("1"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("PRODUCE"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("1"));

    }

    @Test
    void reportCloseLostByIndustry_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-lost-by-industry")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities close-lost in system"));
    }

    @Test
    void reportCloseLostByIndustry_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_LOST, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_LOST, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/closed-lost-by-industry")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("OTHER"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("1"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("PRODUCE"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("1"));

    }

    @Test
    void reportOpenByIndustry_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/open-by-industry")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities open in system"));
    }

    @Test
    void reportOpenByIndustry_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_WON, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_WON, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/open-by-industry")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("OTHER"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("2"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("PRODUCE"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("1"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("MEDICAL"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("1"));
    }

    @Test
    void reportOpportunityByProduct_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/opportunity-by-product")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities in system"));
    }

    @Test
    void reportOpportunityByProduct_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_WON, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_WON, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/opportunity-by-product")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("FLATBED"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("7"));
    }

    @Test
    void reportOpportunityByCountry_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/opportunity-by-country")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities in system"));
    }

    @Test
    void reportOpportunityByCountry_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_WON, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_WON, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/opportunity-by-country")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Spain"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("7"));
    }

    @Test
    void reportOpportunityByCity_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/opportunity-by-city")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities in system"));
    }

    @Test
    void reportOpportunityByCity_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_WON, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_WON, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/opportunity-by-city")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Madrid"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("3"));
    }

    @Test
    void reportOpportunityIndustry_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/opportunity-by-industry")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not opportunities in system"));
    }

    @Test
    void reportOpportunityIndustry_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_WON, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_WON, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/opportunity-by-industry")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("OTHER"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("3"));
    }

    @Test
    void reportOpportunityBySalesRep_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/opportunity-by-sales")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not leads in system"));
    }

    @Test
    void reportOpportunityBySalesRep_IsOk_OppsInDatabase() throws Exception {

        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        User user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        User user2 = new User("user two", UserType.SALESREPRESENTATIVE, "12344578", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);
        userRepository.save(user2);

        contact = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact.setUser(user);
        contact.setAccount(account1);
        account1.getContactList().add(contact);
        contactRepository.save(contact);

        Opportunity  opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user2, contact, account1);
        Opportunity   opportunity2 = new Opportunity(Product.FLATBED, 90, Status.CLOSED_WON, user, contact, account1);
        Opportunity   opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        Opportunity   opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        Opportunity   opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_WON, user, contact, account2);
        Opportunity   opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6));
        Account account4 = new Account(Industry.MEDICAL, 100, "Sevilla", "Spain");
        accountRepository.save(account4);

        Opportunity opportunity7 = new Opportunity();
        opportunity7.setAccount(account4);
        opportunityRepository.save(opportunity7);

        MvcResult mvcResult = mockMvc.perform(get("/report/opportunity-by-sales")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("user two"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("1"));
    }

    @Test
    void reportLeadBySalesRep_IsOk_NoOppsInDatabase() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/report/leads-by-sales")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("There are not leads in system"));
    }

    @Test
    void reportLeadBySalesRep_IsOk_OppsInDatabase() throws Exception {

        User userOne;
        userOne = new User("user one", UserType.SALESREPRESENTATIVE, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(userOne);

        Lead leadOne = new Lead("lead one", "641564611", "leadone@test.com", "Company test 1", new Date(), new Date(0L), "user one", "user one", userOne);
        Lead leadTwo = new Lead("lead two", "641564612", "leadtwo@test.com", "Company test 2", new Date(), new Date(0L), "user one", "user one", userOne);

        leadRepository.saveAll(List.of(leadOne, leadTwo));

        List<Lead> leadList = leadRepository.findByUserUserType(UserType.SALESREPRESENTATIVE);

        MvcResult mvcResult = mockMvc.perform(get("/report/leads-by-sales")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("user one"));
    }
}
