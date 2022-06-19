package com.ironhack.operativeproxy.controller.interfaces;

import com.ironhack.operativeproxy.controller.dto.ConvertInputDTO;

public interface ConvertController {

    void convertLead(int id, ConvertInputDTO convertInputDTO, int userId);
}
