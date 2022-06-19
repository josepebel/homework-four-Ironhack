package com.ironhack.operativeproxy.service.interfaces;

import com.ironhack.operativeproxy.controller.dto.LeadInputDTO;
import com.ironhack.operativeproxy.controller.dto.LeadOutputDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface LeadService {

    LeadOutputDTO createLead(LeadInputDTO leadInputDTO, int userId);
    List<LeadOutputDTO> showLeads();
    LeadOutputDTO getLead(Integer id);
}
