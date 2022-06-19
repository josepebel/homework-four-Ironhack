package com.ironhack.edgeservice.repository;

import com.ironhack.edgeservice.enums.Industry;
import com.ironhack.edgeservice.enums.Product;
import com.ironhack.edgeservice.enums.Status;
import com.ironhack.edgeservice.enums.UserType;
import com.ironhack.edgeservice.model.Account;
import com.ironhack.edgeservice.model.Opportunity;
import com.ironhack.edgeservice.model.User;
import com.ironhack.edgeservice.model.person.Contact;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OpportunityRepository opportunityRepository;

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
        opportunity4 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_LOST, user, contact, account2);
        opportunity5 = new Opportunity(Product.FLATBED, 50, Status.CLOSED_WON, user, contact, account2);
        opportunity6 = new Opportunity(Product.FLATBED, 5, Status.CLOSED_WON, user, contact, account3);
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
    void findById_ReturnEmpty_AccountNotFound() {
        Optional<Account> optionalAccount = accountRepository.findById(-1);
        assertFalse(optionalAccount.isPresent());

        optionalAccount = accountRepository.findById(0);
        assertFalse(optionalAccount.isPresent());
    }

    @Test
    void findById_ReturnAccount_OpportunityFound() {
        Optional<Account> optionalAccount = accountRepository.findById(account1.getId());
        assertTrue(optionalAccount.isPresent());
        assertEquals(200, optionalAccount.get().getEmployeeCount());

        optionalAccount = accountRepository.findById(account2.getId());
        assertTrue(optionalAccount.isPresent());
        assertEquals(60, optionalAccount.get().getEmployeeCount());
    }

    @Test
    void medianEmployeeCount_Return100_OddNumberOfRows() {
        assertEquals(100, accountRepository.medianEmployeeCount().intValue());
    }

    @Test
    void medianEmployeeCount_Return90_EvenNumberOfRows() {
        Account account4 = new Account(Industry.OTHER, 80, "Madrid", "Spain");
        accountRepository.save(account4);
        assertEquals(90, accountRepository.medianEmployeeCount().intValue());
        accountRepository.delete(account4);
    }

    @Test
    void meanEmployeeCount_Return120() {
        assertEquals(120, accountRepository.meanEmployeeCount().intValue());
    }

    @Test
    void maxEmployeeCount_Return200() {
        List<Object[]> result = accountRepository.maxEmployeeCount();

        assertEquals(account1.getId(), result.get(0)[0]);
        assertEquals(200, result.get(0)[1]);
    }

    @Test
    void minEmployeeCount_Return60() {
        List<Object[]> result = accountRepository.minEmployeeCount();
        assertEquals(account2.getId(), result.get(0)[0]);
        assertEquals(60, result.get(0)[1]);
    }


    @Test
    void countOppsByProduct_ReturnFLATBED6() {
        List<Object[]> result = accountRepository.countOppsByProduct();
        assertEquals(Product.FLATBED, result.get(0)[0]);
        assertEquals(Long.valueOf(6), result.get(0)[1]);
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"CLOSED_WON"})
    void countOppsByStatusAndProduct_StatusClosedWon2(Status status){
        List<Object[]> result = accountRepository.countOppsByStatusAndProduct(status);
        assertEquals(Product.FLATBED, result.get(0)[0]);
        assertEquals(Long.valueOf(2), result.get(0)[1]);
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"CLOSED_LOST"})
    void countOppsByStatusAndProduct_StatusClosedLost1(Status status){
        List<Object[]> result = accountRepository.countOppsByStatusAndProduct(status);
        assertEquals(Product.FLATBED, result.get(0)[0]);
        assertEquals(Long.valueOf(1), result.get(0)[1]);
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"OPEN"})
    void countOppsByStatusAndProduct_StatusOpen3(Status status){
        List<Object[]> result = accountRepository.countOppsByStatusAndProduct(status);
        assertEquals(Product.FLATBED, result.get(0)[0]);
        assertEquals(Long.valueOf(3), result.get(0)[1]);
    }

    @Test
    void countOppsByCountry_ReturnSpain6() {
        List<Object[]> result = accountRepository.countOppsByCountry();
        assertEquals("Spain", result.get(0)[0]);
        assertEquals(Long.valueOf(6), result.get(0)[1]);
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"OPEN"})
    void countOppsByStatusAndCountry_ReturnSpain3_StatusOpen(Status status) {
        List<Object[]> result = accountRepository.countOppsByStatusAndCountry(status);
        assertEquals("Spain", result.get(0)[0]);
        assertEquals(Long.valueOf(3), result.get(0)[1]);
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"CLOSED_WON"})
    void countOppsByStatusAndCountry_ReturnSpain2_StatusClosedWon(Status status) {
        List<Object[]> result = accountRepository.countOppsByStatusAndCountry(status);
        assertEquals("Spain", result.get(0)[0]);
        assertEquals(Long.valueOf(2), result.get(0)[1]);
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"CLOSED_LOST"})
    void countOppsByStatusAndCountry_ReturnSpain1_StatusClosedLost(Status status) {
        List<Object[]> result = accountRepository.countOppsByStatusAndCountry(status);
        assertEquals("Spain", result.get(0)[0]);
        assertEquals(Long.valueOf(1), result.get(0)[1]);
    }

    @Test
    void countOppsByCity_ReturnBarcelona1() {
        List<Object[]> result = accountRepository.countOppsByCity();
        assertEquals("Barcelona", result.get(2)[0]);
        assertEquals(Long.valueOf(1), result.get(2)[1]);
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"OPEN"})
    void countOppsByStatusAndCity_ReturnMadrid3_StatusOpen(Status status) {
        List<Object[]> result = accountRepository.countOppsByStatusAndCity(status);
        assertEquals("Madrid", result.get(0)[0]);
        assertEquals(Long.valueOf(3), result.get(0)[1]);
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"CLOSED_LOST"})
    void countOppsByStatusAndCity_ReturnCartagena1_StatusClosedLost(Status status) {
        List<Object[]> result = accountRepository.countOppsByStatusAndCity(status);
        assertEquals("Cartagena", result.get(0)[0]);
        assertEquals(Long.valueOf(1), result.get(0)[1]);
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"CLOSED_WON"})
    void countOppsByStatusAndCity_ReturnBarcelona1_StatusClosedWon(Status status) {
        List<Object[]> result = accountRepository.countOppsByStatusAndCity(status);
        assertEquals("Barcelona", result.get(1)[0]);
        assertEquals(Long.valueOf(1), result.get(1)[1]);
    }


    @Test
    void countOppsByIndustry_ReturnIndustryOther3() {
        List<Object[]> result = accountRepository.countOppsByIndustry();
        assertEquals(Industry.OTHER, result.get(0)[0]);
        assertEquals(Long.valueOf(3), result.get(0)[1]);
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"OPEN"})
    void countOppsByStatusAndIndustry_ReturnIndustryOther3_StatusOpen(Status status) {
        List<Object[]> result = accountRepository.countOppsByStatusAndIndustry(status);
        assertEquals(Industry.OTHER, result.get(0)[0]);
        assertEquals(Long.valueOf(3), result.get(0)[1]);
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"CLOSED_WON"})
    void countOppsByStatusAndIndustry_ReturnIndustryMedical1_StatusClosedWon(Status status) {
        List<Object[]> result = accountRepository.countOppsByStatusAndIndustry(status);
        assertEquals(Industry.MEDICAL, result.get(1)[0]);
        assertEquals(Long.valueOf(1), result.get(1)[1]);
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"CLOSED_LOST"})
    void countOppsByStatusAndIndustry_ReturnIndustryProduce1_StatusClosedLost(Status status) {
        List<Object[]> result = accountRepository.countOppsByStatusAndIndustry(status);
        assertEquals(Industry.PRODUCE, result.get(0)[0]);
        assertEquals(Long.valueOf(1), result.get(0)[1]);
    }
}