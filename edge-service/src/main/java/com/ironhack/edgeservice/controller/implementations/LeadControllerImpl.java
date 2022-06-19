package com.ironhack.edgeservice.controller.implementations;

import com.ironhack.edgeservice.controller.dto.LeadInputDTO;
import com.ironhack.edgeservice.controller.dto.LeadOutputDTO;
import com.ironhack.edgeservice.controller.interfaces.LeadController;
import com.ironhack.edgeservice.security.CustomUserDetails;
import com.ironhack.edgeservice.service.interfaces.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public LeadOutputDTO createLead(@RequestBody @Valid LeadInputDTO leadsInputDTO, @AuthenticationPrincipal CustomUserDetails user) {
        return leadService.createLead(leadsInputDTO, user);
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
