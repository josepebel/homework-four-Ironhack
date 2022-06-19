package com.ironhack.edgeservice.controller.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.edgeservice.controller.dto.UserInputDTO;
import com.ironhack.edgeservice.enums.UserType;
import com.ironhack.edgeservice.model.Role;
import com.ironhack.edgeservice.model.User;
import com.ironhack.edgeservice.repository.RoleRepository;
import com.ironhack.edgeservice.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerImplTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private UserInputDTO userInputDTO;
    private User user;
    private Role role;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        userInputDTO = new UserInputDTO();
        user = new User();
        role = new Role();
    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addUser_UnprocessedEntity_BodyNotAbleToProcess() throws Exception {

        userInputDTO.setName("");
        userInputDTO.setSharedKey("");
        String body = objectMapper.writeValueAsString(userInputDTO);
        mockMvc.perform(post("/users")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addUser_UnprocessedEntity_ResourceExists() throws Exception {

        user.setName("James");
        user.setPasswordAccess("123456");
        user.setUserType(UserType.DIRECTOR);
        userRepository.save(user);
        role.setName("DIRECTOR");
        role.setUser(user);
        roleRepository.save(role);

        userInputDTO.setName("James");
        userInputDTO.setSharedKey("123456");
        userInputDTO.setRole("DIRECTOR");
        String body = objectMapper.writeValueAsString(userInputDTO);
        mockMvc.perform(post("/users")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addUser_UnprocessedEntity_InvalidRole() throws Exception {

        userInputDTO.setName("James");
        userInputDTO.setSharedKey("123456");
        userInputDTO.setRole("INVENTED ROLE");
        String body = objectMapper.writeValueAsString(userInputDTO);
        mockMvc.perform(post("/users")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addThirdParty_Created_StoreUserToDatabase() throws Exception {

        userInputDTO.setName("James");
        userInputDTO.setSharedKey("123456");
        userInputDTO.setRole("DIRECTOR");
        String body = objectMapper.writeValueAsString(userInputDTO);
        MvcResult mvcResult = mockMvc.perform(post("/users")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isCreated())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("James"));

        userInputDTO.setName("Brad");
        userInputDTO.setSharedKey("123456");
        userInputDTO.setRole("SALESREPRESENTATIVE");
        body = objectMapper.writeValueAsString(userInputDTO);
        mvcResult = mockMvc.perform(post("/users")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isCreated())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("Brad"));
    }
}