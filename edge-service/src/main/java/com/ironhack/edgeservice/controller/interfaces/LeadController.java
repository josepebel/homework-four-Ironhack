package com.ironhack.edgeservice.controller.interfaces;

import com.ironhack.edgeservice.controller.dto.LeadInputDTO;
import com.ironhack.edgeservice.controller.dto.LeadOutputDTO;
import com.ironhack.edgeservice.security.CustomUserDetails;

import java.util.List;

public interface LeadController {

    LeadOutputDTO createLead(LeadInputDTO leadsInputDTO,  CustomUserDetails user);
    List<LeadOutputDTO> showLeads();
    LeadOutputDTO getLead(Integer id);
}
