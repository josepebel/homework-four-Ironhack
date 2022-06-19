package com.ironhack.edgeservice.service.interfaces;

import com.ironhack.edgeservice.controller.dto.UserInputDTO;
import com.ironhack.edgeservice.controller.dto.UserOutputDTO;

public interface UserService {

    UserOutputDTO addUser(UserInputDTO userInputDTO);
}
