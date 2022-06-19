package com.ironhack.operativeproxy.controller.interfaces;

import com.ironhack.operativeproxy.controller.dto.LeadInputDTO;
import com.ironhack.operativeproxy.controller.dto.LeadOutputDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface LeadController {

    LeadOutputDTO createLead(LeadInputDTO leadsInputDTO, int userId);
    List<LeadOutputDTO> showLeads();
    LeadOutputDTO getLead(Integer id);
}
