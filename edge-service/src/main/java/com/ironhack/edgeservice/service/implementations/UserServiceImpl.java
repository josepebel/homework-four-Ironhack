package com.ironhack.edgeservice.service.implementations;

import com.ironhack.edgeservice.controller.dto.UserInputDTO;
import com.ironhack.edgeservice.controller.dto.UserOutputDTO;
import com.ironhack.edgeservice.enums.UserType;
import com.ironhack.edgeservice.model.Role;
import com.ironhack.edgeservice.model.User;
import com.ironhack.edgeservice.repository.RoleRepository;
import com.ironhack.edgeservice.repository.UserRepository;
import com.ironhack.edgeservice.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserOutputDTO addUser(UserInputDTO userInputDTO) {

        if ((userInputDTO.getName().equals("")) || (userInputDTO.getSharedKey().equals("")) ) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Body not able to process");
        }

        if ((!userInputDTO.getRole().equals("DIRECTOR")) && (!userInputDTO.getRole().equals("SALESREPRESENTATIVE"))) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid Role");
        }

        Optional<User> userDatabase = userRepository.findByName(userInputDTO.getName());
        if (userDatabase.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Resource already exits");
        }

        User userToAdd = new User();
        userToAdd.setName(userInputDTO.getName());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userToAdd.setPasswordAccess(passwordEncoder.encode(userInputDTO.getSharedKey()));
        userToAdd.setStatus("Active");

        Role role = new Role();

        if (userInputDTO.getRole().equals("DIRECTOR")) {
            userToAdd.setUserType(UserType.DIRECTOR);
            role.setName("DIRECTOR");
        } else {
            userToAdd.setUserType(UserType.SALESREPRESENTATIVE);
            role.setName("SALESREPRESENTATIVE");
        }
        userRepository.save(userToAdd);

        role.setUser(userToAdd);
        roleRepository.save(role);

        UserOutputDTO userOutputDTO = new UserOutputDTO();
        userOutputDTO.setId(userToAdd.getId());
        userOutputDTO.setName(userToAdd.getName());
        userOutputDTO.setRole(userInputDTO.getRole());

        return userOutputDTO;
    }
}
