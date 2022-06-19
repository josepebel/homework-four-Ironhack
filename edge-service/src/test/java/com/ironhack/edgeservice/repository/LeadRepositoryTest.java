package com.ironhack.edgeservice.repository;

import com.ironhack.edgeservice.enums.UserType;
import com.ironhack.edgeservice.model.User;
import com.ironhack.edgeservice.model.person.Lead;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LeadRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LeadRepository leadRepository;

    private User userOne;
    private User userTwo;

    private Lead leadOne;
    private Lead leadTwo;
    private Lead leadThree;
    private Lead leadFour;
    private Lead leadFive;
    private Lead leadSix;

    @BeforeEach
    void setUp() {
        userOne = new User("user one", UserType.SALESREPRESENTATIVE, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userTwo = new User("user two", UserType.SALESREPRESENTATIVE, "12345678", "ACTIVE", new Date(),
                new Date(0L), "test", "");
        userRepository.saveAll(List.of(userOne, userTwo));

        leadOne = new Lead("lead one", "641564611", "leadone@test.com", "Company test 1", new Date(), new Date(0L), "user one", "user one", userOne);
        leadTwo = new Lead("lead two", "641564612", "leadtwo@test.com", "Company test 2", new Date(), new Date(0L), "user one", "user one", userOne);
        leadThree = new Lead("lead three", "641564613", "leadthree@test.com", "Company test 3", new Date(), new Date(0L), "user two", "user one", userTwo);
        leadFour = new Lead("lead four", "641564614", "leadfour@test.com", "Company test 4", new Date(), new Date(0L), "user two", "user one", userTwo);
        leadFive = new Lead("lead five", "641564615", "leadfive@test.com", "Company test 5", new Date(), new Date(0L), "user two", "user one", userTwo);
        leadSix = new Lead("lead six", "641564616", "leadsix@test.com", "Company test 6", new Date(), new Date(0L), "user two", "user one", userTwo);
        leadRepository.saveAll(List.of(leadOne, leadTwo, leadThree, leadFour, leadFive, leadSix));
    }

    @AfterEach
    void tearDown() {
        leadRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getAll() {
        List<Lead> leadList = leadRepository.findAll();
        assertEquals(6, leadList.size());
    }

    @Test
    void findById_ReturnEmpty_LeadNotFound() {
        Optional<Lead> optionalLead = leadRepository.findById(-1);
        assertFalse(optionalLead.isPresent());

        optionalLead = leadRepository.findById(0);
        assertFalse(optionalLead.isPresent());
    }

    @Test
    void findById_ReturnAccount_OpportunityFound() {
        Optional<Lead> optionalLead = leadRepository.findById(leadOne.getId());
        assertTrue(optionalLead.isPresent());
        assertEquals("641564611", optionalLead.get().getPhoneNumber());

        optionalLead = leadRepository.findById(leadTwo.getId());
        assertTrue(optionalLead.isPresent());
        assertEquals("641564612", optionalLead.get().getPhoneNumber());
    }

    @Test
    void findByUserUserType_ReturnLeadList_UserSalesRepresentatives() {
        List<Lead> leadList = leadRepository.findByUserUserType(UserType.SALESREPRESENTATIVE);
        assertEquals(6, leadList.size());
        assertEquals("lead one", leadList.get(0).getName());
        assertEquals("lead two", leadList.get(1).getName());
        assertEquals("lead three", leadList.get(2).getName());
        assertEquals("lead four", leadList.get(3).getName());
        assertEquals("lead five", leadList.get(4).getName());
        assertEquals("lead six", leadList.get(5).getName());
    }

    @Test
    void getCountOfLeadsBySalesRep_ReturnObjectList() {
        List<Object[]> countList = leadRepository.getCountOfLeadsBySalesRep();
        assertEquals(2, countList.size());
        assertEquals("user one", countList.get(0)[0]);
        assertEquals("user two", countList.get(1)[0]);
        assertEquals(BigInteger.valueOf(2), countList.get(0)[1]);
        assertEquals(BigInteger.valueOf(4), countList.get(1)[1]);
    }

    @Test
    void findAll_ReturnEmptyList_NoLeadInRepository() {
        leadRepository.deleteAll();
        List<Lead> leadList = leadRepository.findAll();
        assertEquals(0, leadList.size());
    }

    @Test
    void findAll_ReturnLeadList_LeadInRepository() {
        List<Lead> leadList = leadRepository.findAll();
        assertEquals(6, leadList.size());
        assertEquals(leadOne, leadList.get(0));
        assertEquals(leadTwo, leadList.get(1));
        assertEquals(leadThree, leadList.get(2));
        assertEquals(leadFour, leadList.get(3));
        assertEquals(leadFive, leadList.get(4));
        assertEquals(leadSix, leadList.get(5));
    }

    @Test
    void findByUserId_ReturnEmpty_UserNotFound() {
        List<Lead> leadList = leadRepository.findByUserId(99);
        assertEquals(0, leadList.size());

    }

    @Test
    void findByUserId_ReturnListOfLeads_UserFound() {
        List<Lead> leadList1 = leadRepository.findByUserId(userOne.getId());
        assertEquals(2, leadList1.size());

        List<Lead> leadList2 = leadRepository.findByUserId(userTwo.getId());
        assertEquals(4, leadList2.size());

    }
}