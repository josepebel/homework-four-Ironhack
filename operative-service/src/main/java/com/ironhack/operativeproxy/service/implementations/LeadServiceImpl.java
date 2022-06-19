package com.ironhack.operativeproxy.service.implementations;


import com.ironhack.operativeproxy.controller.dto.LeadInputDTO;
import com.ironhack.operativeproxy.controller.dto.LeadOutputDTO;
import com.ironhack.operativeproxy.model.User;
import com.ironhack.operativeproxy.model.person.Lead;
import com.ironhack.operativeproxy.repository.LeadRepository;
import com.ironhack.operativeproxy.repository.UserRepository;
import com.ironhack.operativeproxy.service.interfaces.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LeadServiceImpl implements LeadService {

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public LeadOutputDTO createLead(LeadInputDTO LeadInputDTO, int userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {

            Lead lead = new Lead(LeadInputDTO.getName(), LeadInputDTO.getPhoneNumber(), LeadInputDTO.getEmail(), LeadInputDTO.getCompanyName(),
                    new Date(), new Date(0L), optionalUser.get().getName(), "", optionalUser.get());
            leadRepository.save(lead);

            LeadOutputDTO leadOutputDTO = new LeadOutputDTO();
            leadOutputDTO.setId(lead.getId());
            leadOutputDTO.setName(lead.getName());
            leadOutputDTO.setPhoneNumber(lead.getPhoneNumber());
            leadOutputDTO.setEmail(lead.getEmail());
            leadOutputDTO.setCompanyName(lead.getCompanyName());
            leadOutputDTO.setCreationDate(lead.getCreationDate());
            leadOutputDTO.setUserCreation(lead.getUserCreation());

            return leadOutputDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
    }


    @Override
    public List<LeadOutputDTO> showLeads() {


        List<Lead> leadList = leadRepository.findAll();

        if (leadList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Leads in application found");
        }
        List<LeadOutputDTO> outputDTOList = new ArrayList<>();
        for (Lead lead : leadList) {
            LeadOutputDTO leadOutputDTO = new LeadOutputDTO();
            leadOutputDTO.setId(lead.getId());
            leadOutputDTO.setName(lead.getName());
            leadOutputDTO.setPhoneNumber(lead.getPhoneNumber());
            leadOutputDTO.setEmail(lead.getEmail());
            leadOutputDTO.setCompanyName(lead.getCompanyName());
            leadOutputDTO.setCreationDate(lead.getCreationDate());
            leadOutputDTO.setUserCreation(lead.getUserCreation());
            outputDTOList.add(leadOutputDTO);
        }
        return outputDTOList;
    }


    @Override
    public LeadOutputDTO getLead(Integer id) {

        Lead lead = leadRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lead for that id doesn't exists"));
        LeadOutputDTO leadOutputDTO = new LeadOutputDTO();
        leadOutputDTO.setId(lead.getId());
        leadOutputDTO.setName(lead.getName());
        leadOutputDTO.setPhoneNumber(lead.getPhoneNumber());
        leadOutputDTO.setEmail(lead.getEmail());
        leadOutputDTO.setCompanyName(lead.getCompanyName());
        leadOutputDTO.setCreationDate(lead.getCreationDate());
        leadOutputDTO.setUserCreation(lead.getUserCreation());

        return leadOutputDTO;


    }

}
