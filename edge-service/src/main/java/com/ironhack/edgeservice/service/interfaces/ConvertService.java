package com.ironhack.edgeservice.service.interfaces;

import com.ironhack.edgeservice.controller.dto.ConvertInputDTO;
import com.ironhack.edgeservice.security.CustomUserDetails;

public interface ConvertService {

    void convertLead(int id, ConvertInputDTO convertInputDTO, CustomUserDetails user);
}
