package com.ironhack.operativeproxy.service.interfaces;

import com.ironhack.operativeproxy.controller.dto.ConvertInputDTO;

public interface ConvertService {

    void convertLead(int id, ConvertInputDTO convertInputDTO, int userId);
}
