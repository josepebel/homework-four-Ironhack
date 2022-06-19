package com.ironhack.edgeservice.service.interfaces;

import com.ironhack.edgeservice.controller.dto.OpportunityOutputDTO;

import java.util.List;

public interface OpportunityService {

    List<OpportunityOutputDTO> showOpportunitiesByUserId(int id);
    OpportunityOutputDTO updateOpportunityCloseLost(int id);
    OpportunityOutputDTO updateOpportunityCloseWon(int id);
    String meanQuantity();
    String medianQuantity();
    String maxQuantity();
    String minQuantity();

}
