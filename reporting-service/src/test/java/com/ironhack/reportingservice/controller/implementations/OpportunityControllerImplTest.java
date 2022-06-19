package com.ironhack.reportingservice.controller.implementations;

import com.ironhack.reportingservice.enums.Industry;
import com.ironhack.reportingservice.enums.Product;
import com.ironhack.reportingservice.enums.Status;
import com.ironhack.reportingservice.enums.UserType;
import com.ironhack.reportingservice.model.Account;
import com.ironhack.reportingservice.model.Opportunity;
import com.ironhack.reportingservice.model.User;
import com.ironhack.reportingservice.model.person.Contact;
import com.ironhack.reportingservice.repository.AccountRepository;
import com.ironhack.reportingservice.repository.ContactRepository;
import com.ironhack.reportingservice.repository.OpportunityRepository;
import com.ironhack.reportingservice.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OpportunityControllerImplTest {

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

    private MockMvc mockMvc;

    private User userOne;
    private Contact contact;
    private Opportunity opportunity;
    private Opportunity opportunity1;
    private Opportunity opportunity2;
    private Opportunity opportunity3;
    private Opportunity opportunity4;
    private Opportunity opportunity5;
    private Opportunity opportunity6;
    private Opportunity opportunity7;
    private Account account1;
    private Account account2;
    private Account account3;
    private Account account4;

    private User user;
    private Contact contact1;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        userOne = new User("user one", UserType.SALESREPRESENTATIVE, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(userOne);

        contact = new Contact("contact", "123456789", "contact@email.com", "contact S.A.",
                new Date(), new Date(), "test", "", null);
        contactRepository.save(contact);

        opportunity = new Opportunity();
        opportunity.setProduct(Product.BOX);
        opportunity.setStatus(Status.OPEN);
        opportunity.setQuantity(33);
        opportunity.setUser(userOne);
        opportunity.setDecisionMaker(contact);
        opportunityRepository.save(opportunity);

        account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

        user = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(user);

        contact1 = new Contact("Isaac García", "374642823", "isaacgarcia@gmail.es", "Telefónica");
        contact1.setUser(user);
        contact1.setAccount(account1);
        account1.getContactList().add(contact1);
        contactRepository.save(contact1);

        opportunity1 = new Opportunity(Product.FLATBED, 100, Status.OPEN, user, contact, account1);
        opportunity2 = new Opportunity(Product.FLATBED, 90, Status.OPEN, user, contact, account1);
        opportunity3 = new Opportunity(Product.FLATBED, 75, Status.OPEN, user, contact, account1);
        opportunity4 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        opportunity5 = new Opportunity(Product.FLATBED, 50, Status.OPEN, user, contact, account2);
        opportunity6 = new Opportunity(Product.FLATBED, 5, Status.OPEN, user, contact, account3);
        opportunity7 = new Opportunity(Product.FLATBED, 10, Status.OPEN, user, contact, account1);

        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4, opportunity5, opportunity6, opportunity7));
    }

    @AfterEach
    void tearDown() {
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void meanOpportunityQuantity_noOpportunities() throws Exception {

        opportunityRepository.deleteAll();

        MvcResult mvcResult = mockMvc.perform(get("/opportunity-quantity/mean")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Mean Quantity is: 0"));
    }

    @Test
    void meanOpportunityQuantity_opportunities_meanCount() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/opportunity-quantity/mean")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Mean Quantity is: 51.625"));
    }

    @Test
    void medianOpportunityQuantity_noOpportunities() throws Exception {

        opportunityRepository.deleteAll();
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-quantity/median")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Median Quantity is: 0"));
    }

    @Test
    void medianOpportunityQuantity_opportunities_median() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/opportunity-quantity/median")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Median Quantity is: 50.0000"));
    }

    @Test
    void maxOpportunityQuantity_noOpportunities() throws Exception {

        opportunityRepository.deleteAll();
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-quantity/max")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Max Quantity is: 0"));
    }

    @Test
    void maxOpportunityQuantity_opportunities_max() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/opportunity-quantity/max")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        System.out.println(mvcResult.getResponse());
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Max Quantity is: 100"));
    }

    @Test
    void minOpportunityQuantity_noOpportunities() throws Exception {

        opportunityRepository.deleteAll();
        MvcResult mvcResult = mockMvc.perform(get("/opportunity-quantity/min")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Min Quantity is: 0"));
    }

    @Test
    void minOpportunityQuantity_opportunities_min() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/opportunity-quantity/min")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Min Quantity is: 5"));
    }
}