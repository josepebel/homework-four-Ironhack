package com.ironhack.reportingservice.controller.implementations;

import com.ironhack.reportingservice.enums.Industry;
import com.ironhack.reportingservice.model.Account;
import com.ironhack.reportingservice.repository.AccountRepository;
import com.ironhack.reportingservice.repository.ContactRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class EmployeeControllerImplTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ContactRepository contactRepository;

    private Account account1;
    private Account account2;
    private Account account3;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
        Account account1 = new Account(Industry.OTHER, 200, "Madrid", "Spain");
        Account account2 = new Account(Industry.PRODUCE, 60, "Cartagena", "Spain");
        Account account3 = new Account(Industry.MEDICAL, 100, "Barcelona", "Spain");
        accountRepository.saveAll(List.of(account1, account2, account3));

    }

    @AfterEach
    void tearDown() {
        contactRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void meanEmployeeCount_noEmployees() throws Exception {
        accountRepository.deleteAll();
        MvcResult mvcResult = mockMvc.perform(get("/employee-count/mean")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Mean Employee Count is: 0"));
    }

    @Test
    void meanEmployeeCount_employees_meanCount() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/employee-count/mean")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Mean Employee Count is: 120"));
    }

    @Test
    void medianEmployeeCount_noEmployees() throws Exception {
        accountRepository.deleteAll();
        MvcResult mvcResult = mockMvc.perform(get("/employee-count/median")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Median Employee Count is: 0"));
    }

    @Test
    void medianEmployeeCount_employees_median() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/employee-count/median")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Median Employee Count is: 100"));
    }

    @Test
    void maxEmployeeCount_noEmployees() throws Exception {
        accountRepository.deleteAll();
        MvcResult mvcResult = mockMvc.perform(get("/employee-count/max")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Max Employee Count is: 0"));
    }

    @Test
    void maxEmployeeCount_employees_max() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/employee-count/max")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Max Employee Count is: 200"));
    }

    @Test
    void minEmployeeCount_noEmployees() throws Exception {
        accountRepository.deleteAll();
        MvcResult mvcResult = mockMvc.perform(get("/employee-count/min")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Min Employee Count is: 0"));
    }

    @Test
    void minEmployeeCount_employees_min() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/employee-count/min")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Min Employee Count is: 60"));
    }
}