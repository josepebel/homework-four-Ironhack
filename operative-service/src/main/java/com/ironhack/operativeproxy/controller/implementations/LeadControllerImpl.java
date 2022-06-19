package com.ironhack.operativeproxy.controller.implementations;


import com.ironhack.operativeproxy.controller.dto.LeadInputDTO;
import com.ironhack.operativeproxy.controller.dto.LeadOutputDTO;
import com.ironhack.operativeproxy.controller.interfaces.LeadController;
import com.ironhack.operativeproxy.service.interfaces.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class LeadControllerImpl implements LeadController {

    @Autowired
    private LeadService leadService;

    @Override
    @PostMapping("/new-lead")
    @ResponseStatus(HttpStatus.CREATED)
    public LeadOutputDTO createLead(@RequestBody @Valid LeadInputDTO leadsInputDTO, @RequestParam int userId) {
        return leadService.createLead(leadsInputDTO, userId);
    }

    @Override
    @GetMapping("/show-leads")
    @ResponseStatus(HttpStatus.OK)
    public List<LeadOutputDTO> showLeads() {

        return leadService.showLeads();
    }

    @Override
    @GetMapping("/lead/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LeadOutputDTO getLead(@PathVariable Integer id) {

        return leadService.getLead(id);
    }
}
