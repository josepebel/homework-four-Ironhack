package com.ironhack.reportingservice.controller.interfaces;

import com.ironhack.reportingservice.controller.dto.OpportunityOutputDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface OpportunityController {

    List<OpportunityOutputDTO> showOpportunitiesByUserId(int id);
    void closeLostOpportunity(@PathVariable int id);
    void closeWonOpportunity(@PathVariable int id);
    String meanQuantity();
    String medianQuantity();
    String maxQuantity();
    String minQuantity();
}
