package com.ironhack.operativeproxy.controller.implementations;

import com.ironhack.operativeproxy.controller.dto.SalesInputDTO;
import com.ironhack.operativeproxy.enums.UserType;
import com.ironhack.operativeproxy.model.User;
import com.ironhack.operativeproxy.repository.RoleRepository;
import com.ironhack.operativeproxy.repository.UserRepository;
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
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SalesControllerImplTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    SalesInputDTO salesInputDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        salesInputDTO = new SalesInputDTO();
    }

    @AfterEach
    void tearDown() {

        roleRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void createSales_badRequest() throws Exception {

        salesInputDTO.setName("pepe");
        String body = objectMapper.writeValueAsString(salesInputDTO);
        mockMvc.perform(post("/new-sales")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isBadRequest());

        salesInputDTO.setName("");
        salesInputDTO.setPassword("123456");
        body = objectMapper.writeValueAsString(salesInputDTO);
        mockMvc.perform(post("/new-sales")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void createSales_Created_StoreUserToDatabase() throws Exception {

        salesInputDTO.setName("pepe");
        salesInputDTO.setPassword("123456");
        String body = objectMapper.writeValueAsString(salesInputDTO);
        MvcResult mvcResult = mockMvc.perform(post("/new-sales")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isCreated())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("pepe"));
    }

    @Test
    void showSales_NotFound_NoSalesInDatabase() throws Exception {
        userRepository.deleteAll();
        mockMvc.perform(get("/show-sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void showSales_isOk_SalesInDatabase() throws Exception {

        User userOne = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        User userTwo = new User("user two", UserType.SALESREPRESENTATIVE, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(userOne);
        userRepository.save(userTwo);
        MvcResult mvcResult = mockMvc.perform(get("/show-sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("user two"));
    }
}