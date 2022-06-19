package com.ironhack.operativeproxy.service.interfaces;

import com.ironhack.operativeproxy.controller.dto.OpportunityOutputDTO;

import java.util.List;

public interface OpportunityService {

    List<OpportunityOutputDTO> showOpportunitiesByUserId(int id);
    OpportunityOutputDTO updateOpportunityCloseLost(int id);
    OpportunityOutputDTO updateOpportunityCloseWon(int id);

}
