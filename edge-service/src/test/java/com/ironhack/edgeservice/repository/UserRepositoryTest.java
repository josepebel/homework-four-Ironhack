package com.ironhack.edgeservice.repository;

import com.ironhack.edgeservice.enums.Product;
import com.ironhack.edgeservice.enums.Status;
import com.ironhack.edgeservice.enums.UserType;
import com.ironhack.edgeservice.model.Opportunity;
import com.ironhack.edgeservice.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OpportunityRepository opportunityRepository;

    private User userOne;
    private User userTwo;
    private User userThree;
    private Opportunity opportunityTwo;
    private Opportunity opportunityThree;
    private Opportunity opportunityFour;

    @BeforeEach
    void setUp() {

        userOne = new User("user one", UserType.DIRECTOR, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userTwo = new User("user two", UserType.SALESREPRESENTATIVE, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        opportunityTwo = new Opportunity(Product.HYBRID, 22, null, Status.OPEN);
        opportunityTwo.setUser(userTwo);
        userTwo.setOpportunityList(List.of(opportunityTwo));
        userThree = new User("user three", UserType.SALESREPRESENTATIVE, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        opportunityThree = new Opportunity(Product.FLATBED, 100, null, Status.CLOSED_LOST);
        opportunityThree.setUser(userThree);
        opportunityFour = new Opportunity(Product.BOX, 33, null, Status.OPEN);
        opportunityFour.setUser(userThree);
        userThree.setOpportunityList(List.of(opportunityThree, opportunityFour));
        userRepository.saveAll(List.of(userOne, userTwo, userThree));
        opportunityRepository.saveAll(List.of(opportunityTwo, opportunityThree, opportunityFour));
    }

    @AfterEach
    void tearDown() {
        opportunityRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void findByName_ValidName_User() {
        Optional<User> optionalUser = userRepository.findByName(userOne.getName());
        assertTrue(optionalUser.isPresent());
        assertEquals("12345678",optionalUser.get().getPasswordAccess());
    }

    @Test
    void findByName_InvalidName_User() {
        Optional<User> optionalUser = userRepository.findByName("prueba");
        assertTrue(optionalUser.isEmpty());
    }

    @Test
    void getAllUserByUserType_ReturnUserList_UserTypeDirector() {
        List<User> userList = userRepository.getAllUserByUserType(UserType.DIRECTOR);
        assertEquals(1, userList.size());
        assertEquals(userOne, userList.get(0));
    }

    @Test
    void getAllUserByUserType_ReturnUserList_UserTypeSalesRepresentative() {
        List<User> userList = userRepository.getAllUserByUserType(UserType.SALESREPRESENTATIVE);
        assertEquals(2, userList.size());
        assertEquals(userTwo, userList.get(0));
        assertEquals(userThree, userList.get(1));
    }

    @Test
    void getAllUserWithOpportunityByUserType_ReturnEmptyList_UserWithoutOpportunities() {
        List<User> userList = userRepository.getAllUserWithOpportunityByUserType(UserType.DIRECTOR);
        assertEquals(1, userList.size());
        assertEquals(0, userList.get(0).getOpportunityList().size());
    }

    @Test
    void getAllUserWithOpportunityByUserType_ReturnOpportunityList_UserWithOpportunities() {
        List<User> userList = userRepository.getAllUserWithOpportunityByUserType(UserType.SALESREPRESENTATIVE);
        assertEquals(2, userList.size());
        assertEquals(1, userList.get(0).getOpportunityList().size());
        assertEquals(opportunityTwo, userList.get(0).getOpportunityList().get(0));
        assertEquals(2, userList.get(1).getOpportunityList().size());
    }

    @Test
    void getAllUserWithOpportunityByUserTypeAndStatus_ReturnEmptyList_UsersWithoutOpportunitiesClosedWon() {
        List<User> userList = userRepository.getAllUserWithOpportunityByUserTypeAndStatus(
                UserType.SALESREPRESENTATIVE, Status.CLOSED_WON);
        assertEquals(0, userList.size());
    }

    @Test
    void getAllUserWithOpportunityByUserTypeAndStatus_ReturnOpportunityList_UsersWithOpportunitiesOpen() {
        List<User> userList = userRepository.getAllUserWithOpportunityByUserTypeAndStatus(
                UserType.SALESREPRESENTATIVE, Status.OPEN);
        assertEquals(2, userList.size());
        assertEquals(1, userList.get(0).getOpportunityList().size());
        assertEquals(1, userList.get(1).getOpportunityList().size());
        assertEquals(opportunityTwo, userList.get(0).getOpportunityList().get(0));
        assertEquals(opportunityFour, userList.get(1).getOpportunityList().get(0));
    }
}