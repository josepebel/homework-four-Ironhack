package com.ironhack.operativeproxy.controller.implementations;

import com.ironhack.operativeproxy.controller.dto.LeadInputDTO;
import com.ironhack.operativeproxy.enums.UserType;
import com.ironhack.operativeproxy.model.User;
import com.ironhack.operativeproxy.model.person.Lead;
import com.ironhack.operativeproxy.repository.LeadRepository;
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
class LeadControllerImplTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private LeadInputDTO leadInputDTO;
    private Lead lead1 = new Lead();
    private Lead lead2 = new Lead();
    private User userOne = new User();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        leadInputDTO = new LeadInputDTO();

        userOne = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(userOne);

        lead1 = new Lead("first lead", "333333333", "lead@mail.com", "Uber", new Date(), new Date(0L), "prueba", "prueba", userOne);
        lead2 = new Lead("second lead", "333333333", "lead@mail.com", "Uber", new Date(), new Date(0L), "prueba", "prueba", userOne);

    }

    @AfterEach
    void tearDown() {
        leadRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void createLead_badRequest() throws Exception {

        leadInputDTO.setName("Jose García");
        leadInputDTO.setPhoneNumber("2536588");
        leadInputDTO.setEmail("jose@mail.com");
        String body = objectMapper.writeValueAsString(leadInputDTO);
        mockMvc.perform(post("/new-lead")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isBadRequest());

        leadInputDTO.setName("");
        leadInputDTO.setPhoneNumber("2536588");
        leadInputDTO.setEmail("jose@mail.com");
        leadInputDTO.setCompanyName("Toyota");
        body = objectMapper.writeValueAsString(leadInputDTO);
        mockMvc.perform(post("/new-lead")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isBadRequest());

        leadInputDTO.setName("");
        leadInputDTO.setPhoneNumber("2536588");
        leadInputDTO.setEmail("");
        leadInputDTO.setCompanyName("Toyota");
        body = objectMapper.writeValueAsString(leadInputDTO);
        mockMvc.perform(post("/new-lead")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isBadRequest());

    }

    @Test
    void createLead_badRequest_IncorrectFormatPhoneNumber() throws Exception {

        leadInputDTO.setName("Jose García");
        leadInputDTO.setPhoneNumber("2588");
        leadInputDTO.setEmail("josemail.com");
        leadInputDTO.setCompanyName("Toyota");
        String body = objectMapper.writeValueAsString(leadInputDTO);
        mockMvc.perform(post("/new-lead")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isBadRequest());



        leadInputDTO.setName("Jose García");
        leadInputDTO.setPhoneNumber("2588888888888888");
        leadInputDTO.setEmail("josemail.com");
        leadInputDTO.setCompanyName("Toyota");
        body = objectMapper.writeValueAsString(leadInputDTO);
        mockMvc.perform(post("/new-lead")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isBadRequest());
    }



    @Test
    void createLead_Created_StoreLeadToDatabase() throws Exception {

        leadInputDTO.setName("Jose García");
        leadInputDTO.setPhoneNumber("2536588");
        leadInputDTO.setEmail("jose@mail.com");
        leadInputDTO.setCompanyName("Toyota");
        String body = objectMapper.writeValueAsString(leadInputDTO);
        MvcResult mvcResult = mockMvc.perform(post("/new-lead"+"?userId="+userOne.getId())
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isCreated())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Jose"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Toyota"));
    }

    @Test
    void showLeads_NotFound_NoLeadInDatabase() throws Exception {

        mockMvc.perform(get("/show-leads")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void showLeads_isOk_LeadsInDatabase() throws Exception {

        leadRepository.save(lead1);
        leadRepository.save(lead2);

        MvcResult mvcResult = mockMvc.perform(get("/show-leads")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("second lead"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("first lead"));
    }

    @Test
    void getLead_IsOk_LeadInDatabase() throws Exception {
        leadRepository.save(lead1);
        leadRepository.save(lead2);

        MvcResult mvcResult = mockMvc.perform(get("/lead/"+lead1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("first lead"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("333333333"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("lead@mail.com"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Uber"));
    }

}