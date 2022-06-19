package com.ironhack.edgeservice.controller.interfaces;

import com.ironhack.edgeservice.controller.dto.UserInputDTO;
import com.ironhack.edgeservice.controller.dto.UserOutputDTO;

public interface UserController {

    UserOutputDTO addUser(UserInputDTO userInputDTO);
}
