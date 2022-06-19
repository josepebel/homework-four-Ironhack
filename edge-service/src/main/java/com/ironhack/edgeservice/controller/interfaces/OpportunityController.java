package com.ironhack.edgeservice.controller.interfaces;

import com.ironhack.edgeservice.controller.dto.OpportunityOutputDTO;

import java.util.List;

public interface OpportunityController {

    List<OpportunityOutputDTO> showOpportunitiesByUserId(int id);
    void closeLostOpportunity(int id);
    void closeWonOpportunity(int id);
    String meanQuantity();
    String medianQuantity();
    String maxQuantity();
    String minQuantity();
}
