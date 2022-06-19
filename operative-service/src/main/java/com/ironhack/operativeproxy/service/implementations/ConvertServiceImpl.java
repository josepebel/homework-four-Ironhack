package com.ironhack.operativeproxy.service.implementations;

import com.ironhack.operativeproxy.controller.dto.ConvertInputDTO;
import com.ironhack.operativeproxy.enums.Status;
import com.ironhack.operativeproxy.enums.UserType;
import com.ironhack.operativeproxy.model.Account;
import com.ironhack.operativeproxy.model.Opportunity;
import com.ironhack.operativeproxy.model.User;
import com.ironhack.operativeproxy.model.person.Contact;
import com.ironhack.operativeproxy.model.person.Lead;
import com.ironhack.operativeproxy.repository.*;
import com.ironhack.operativeproxy.service.interfaces.ConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ConvertServiceImpl implements ConvertService {

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void convertLead(int id, ConvertInputDTO convertInputDTO, int userId) {

        Optional<Lead> optionalLead = leadRepository.findById(id);
        if (optionalLead.isPresent()) {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                Contact contact = new Contact(optionalLead.get().getName(), optionalLead.get().getPhoneNumber(), optionalLead.get().getEmail(), optionalLead.get().getCompanyName(),
                        new Date(), new Date(0L), optionalUser.get().getName(), "", null);
                Opportunity opportunity = new Opportunity(convertInputDTO.getProduct(), convertInputDTO.getQuantity(),
                        Status.OPEN, optionalUser.get(), contact, null, new Date(), new Date(0L), optionalUser.get().getName(), "");
                Account account = new Account(convertInputDTO.getIndustry(), convertInputDTO.getEmployeeCount(), convertInputDTO.getCity(),
                        convertInputDTO.getCountry(), new Date(), new Date(0L), optionalUser.get().getName(), "", List.of(contact), List.of(opportunity));
                accountRepository.save(account);
                contact.setAccount(account);
                opportunity.setAccount(account);
                contactRepository.save(contact);
                opportunityRepository.save(opportunity);
                leadRepository.delete(optionalLead.get());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "There is no Lead with the provided id");
        }
    }
}
