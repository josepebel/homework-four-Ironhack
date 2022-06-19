package com.ironhack.edgeservice.service.interfaces;

import com.ironhack.edgeservice.controller.dto.LeadInputDTO;
import com.ironhack.edgeservice.controller.dto.LeadOutputDTO;
import com.ironhack.edgeservice.security.CustomUserDetails;

import java.util.List;


public interface LeadService {

    LeadOutputDTO createLead(LeadInputDTO leadInputDTO,  CustomUserDetails user);
    List<LeadOutputDTO> showLeads();
    LeadOutputDTO getLead(Integer id);
}
