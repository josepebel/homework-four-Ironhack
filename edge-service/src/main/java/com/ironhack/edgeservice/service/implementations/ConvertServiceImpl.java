package com.ironhack.edgeservice.service.implementations;

import com.ironhack.edgeservice.client.OperativeServiceClient;
import com.ironhack.edgeservice.controller.dto.ConvertInputDTO;
import com.ironhack.edgeservice.model.User;
import com.ironhack.edgeservice.repository.*;
import com.ironhack.edgeservice.security.CustomUserDetails;
import com.ironhack.edgeservice.service.interfaces.ConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ConvertServiceImpl implements ConvertService {

   @Autowired
   private OperativeServiceClient operativeServiceClient;

   @Autowired
   private UserRepository userRepository;

    @Override
    public void convertLead(int id, ConvertInputDTO convertInputDTO, CustomUserDetails user) {

        Optional<User> optionalUser = userRepository.findByName(user.getUsername());
        if (optionalUser.isPresent()) {
            operativeServiceClient.convertLead(id, convertInputDTO, user.getUser().getId());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Authentication error");
        }
    }
}
