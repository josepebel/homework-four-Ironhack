package com.ironhack.operativeproxy.service.implementations;

import com.ironhack.operativeproxy.controller.dto.ConvertInputDTO;
import com.ironhack.operativeproxy.enums.Industry;
import com.ironhack.operativeproxy.enums.Product;
import com.ironhack.operativeproxy.enums.UserType;
import com.ironhack.operativeproxy.model.User;
import com.ironhack.operativeproxy.model.person.Lead;
import com.ironhack.operativeproxy.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ConvertServiceImplTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private OpportunityRepository opportunityRepository;


    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private ConvertInputDTO convertInputDTO;
    private Lead leadOne;
    private User userOne;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        convertInputDTO = new ConvertInputDTO();

        userOne = new User("user two", UserType.SALESREPRESENTATIVE, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.save(userOne);

        leadOne = new Lead("lead one", "641564611L", "leadone@test.com", "Company test 1", new Date(), new Date(0L), "user one", "user one", userOne);
        leadRepository.save(leadOne);
    }

    @AfterEach
    void tearDown() {
        leadRepository.deleteAll();
        opportunityRepository.deleteAll();
    }

    @Test
    void convertLead_badRequest_NoBodyInformed() throws Exception {

        String body = objectMapper.writeValueAsString(convertInputDTO);
        mockMvc.perform(patch("/convert/0")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void convertLead_UnprocessedEntity_NoLeadInDatabase() throws Exception {

        convertInputDTO.setProduct(Product.FLATBED);
        convertInputDTO.setQuantity(3);
        convertInputDTO.setIndustry(Industry.MEDICAL);
        convertInputDTO.setEmployeeCount(100);
        convertInputDTO.setCity("Madrid");
        convertInputDTO.setCountry("Spain");

        String body = objectMapper.writeValueAsString(convertInputDTO);
        mockMvc.perform(patch("/convert/0"+"?userId="+userOne.getId())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isUnprocessableEntity());
    }

//    @Test
//    void convertLead_NotAcceptable_LeadCreatedByOtherSales() throws Exception {
//
//        String body = objectMapper.writeValueAsString(convertInputDTO);
//        mockMvc.perform(patch("/convert/"+leadone.getId())
//                        .content(body)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("UTF-8")
//                )
//                .andExpect(status().isNotAcceptable());
//    }

    @Test
    void convertLead_NoContent_LeadInDatabase() throws Exception {

        convertInputDTO.setProduct(Product.FLATBED);
        convertInputDTO.setQuantity(3);
        convertInputDTO.setIndustry(Industry.MEDICAL);
        convertInputDTO.setEmployeeCount(100);
        convertInputDTO.setCity("Madrid");
        convertInputDTO.setCountry("Spain");

        String body = objectMapper.writeValueAsString(convertInputDTO);
        mockMvc.perform(patch("/convert/"+leadOne.getId()+"?userId="+userOne.getId())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isNoContent());
    }
}