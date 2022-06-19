package com.ironhack.edgeservice.controller.interfaces;

import com.ironhack.edgeservice.controller.dto.ConvertInputDTO;
import com.ironhack.edgeservice.security.CustomUserDetails;

public interface ConvertController {

    void convertLead(int id, ConvertInputDTO convertInputDTO, CustomUserDetails user);
}
