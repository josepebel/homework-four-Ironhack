package com.ironhack.operativeproxy.controller.implementations;

import com.ironhack.operativeproxy.controller.dto.OpportunityOutputDTO;
import com.ironhack.operativeproxy.controller.interfaces.OpportunityController;
import com.ironhack.operativeproxy.service.interfaces.OpportunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OpportunityControllerImpl implements OpportunityController {

    @Autowired
    private OpportunityService opportunityService;

    @Override
    @GetMapping("/show-opportunities/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunityOutputDTO> showOpportunitiesByUserId(@PathVariable int id) {

        return opportunityService.showOpportunitiesByUserId(id);
    }

    @Override
    @PutMapping("/close-lost-opportunity/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeLostOpportunity(@PathVariable int id) {
        opportunityService.updateOpportunityCloseLost(id);
    }

    @Override
    @PutMapping("/close-won-opportunity/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeWonOpportunity(@PathVariable int id) {
        opportunityService.updateOpportunityCloseWon(id);
    }


}
