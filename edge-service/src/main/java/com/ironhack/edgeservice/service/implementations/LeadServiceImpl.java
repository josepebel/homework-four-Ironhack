package com.ironhack.edgeservice.service.implementations;


import com.ironhack.edgeservice.client.OperativeServiceClient;
import com.ironhack.edgeservice.controller.dto.LeadInputDTO;
import com.ironhack.edgeservice.controller.dto.LeadOutputDTO;
import com.ironhack.edgeservice.model.User;
import com.ironhack.edgeservice.model.person.Lead;
import com.ironhack.edgeservice.repository.LeadRepository;
import com.ironhack.edgeservice.repository.UserRepository;
import com.ironhack.edgeservice.security.CustomUserDetails;
import com.ironhack.edgeservice.service.interfaces.LeadService;
import feign.FeignException;
import feign.RetryableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LeadServiceImpl implements LeadService {

    @Autowired
    private OperativeServiceClient operativeServiceClient;

    @Autowired
    private UserRepository userRepository;

    @Override
    public LeadOutputDTO createLead(LeadInputDTO leadInputDTO, CustomUserDetails user) {

        Optional<User> optionalUser = userRepository.findByName(user.getUsername());
        if (optionalUser.isPresent()) {
            return operativeServiceClient.createLead(leadInputDTO, optionalUser.get().getId());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Authentication error");
        }
    }

    @Override
    public List<LeadOutputDTO> showLeads() {

        try {
            return operativeServiceClient.showLeads();
        } catch (RetryableException | FeignException.ServiceUnavailable e1) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Error in proxy service");
        } catch (FeignException.NotFound e2) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data found");
        }
    }

    @Override
    public LeadOutputDTO getLead(Integer id) {
        return operativeServiceClient.getLead(id);
    }

}
