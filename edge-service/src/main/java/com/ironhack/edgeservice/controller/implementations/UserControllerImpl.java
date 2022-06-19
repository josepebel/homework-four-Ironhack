package com.ironhack.edgeservice.controller.implementations;

import com.ironhack.edgeservice.controller.dto.UserInputDTO;
import com.ironhack.edgeservice.controller.dto.UserOutputDTO;
import com.ironhack.edgeservice.controller.interfaces.UserController;
import com.ironhack.edgeservice.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;

    @Override
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutputDTO addUser(@RequestBody UserInputDTO userInputDTO) {

        return userService.addUser(userInputDTO);
    }
}
