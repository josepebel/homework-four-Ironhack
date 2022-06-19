package com.ironhack.operativeproxy.service.implementations;

import com.ironhack.operativeproxy.controller.dto.OpportunityOutputDTO;
import com.ironhack.operativeproxy.enums.Status;
import com.ironhack.operativeproxy.model.Opportunity;
import com.ironhack.operativeproxy.repository.OpportunityRepository;
import com.ironhack.operativeproxy.service.interfaces.OpportunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OpportunityServiceImpl implements OpportunityService {

    @Autowired
    private OpportunityRepository opportunityRepository;


    @Override
    public List<OpportunityOutputDTO> showOpportunitiesByUserId(int id) {

        List<Opportunity> opportunityList = opportunityRepository.findByUserId(id);
        if (opportunityList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There are no opportunities associated with your ID in the app");
        }
        List<OpportunityOutputDTO> returnList = new ArrayList<>();
        for (int i = 0; i < opportunityList.size(); i++) {
            OpportunityOutputDTO opportunityOutputDTO = new OpportunityOutputDTO();
            opportunityOutputDTO.setId(opportunityList.get(i).getId());
            opportunityOutputDTO.setProduct(opportunityList.get(i).getProduct());
            opportunityOutputDTO.setQuantity(opportunityList.get(i).getQuantity());
            opportunityOutputDTO.setName(opportunityList.get(i).getDecisionMaker().getName());
            opportunityOutputDTO.setPhoneNumber(opportunityList.get(i).getDecisionMaker().getPhoneNumber());
            opportunityOutputDTO.setEmail(opportunityList.get(i).getDecisionMaker().getEmail());
            opportunityOutputDTO.setCompanyName(opportunityList.get(i).getDecisionMaker().getCompanyName());
            opportunityOutputDTO.setStatus(opportunityList.get(i).getStatus());

            returnList.add(opportunityOutputDTO);
        }

        return returnList;
    }

    @Override
    public OpportunityOutputDTO updateOpportunityCloseLost(int id) {


        OpportunityOutputDTO closedOpportunity = new OpportunityOutputDTO();
        opportunityRepository.updateOpportunityStatus(id,Status.CLOSED_LOST);

        Optional<Opportunity> opportunity = opportunityRepository.findById(id);
        if (opportunity.isPresent()) {
            closedOpportunity.setId(opportunity.get().getId());
            closedOpportunity.setProduct(opportunity.get().getProduct());
            closedOpportunity.setQuantity(opportunity.get().getQuantity());
            closedOpportunity.setName(opportunity.get().getDecisionMaker().getName());
            closedOpportunity.setPhoneNumber(opportunity.get().getDecisionMaker().getPhoneNumber());
            closedOpportunity.setEmail(opportunity.get().getDecisionMaker().getEmail());
            closedOpportunity.setCompanyName(opportunity.get().getDecisionMaker().getCompanyName());
            closedOpportunity.setStatus(opportunity.get().getStatus());

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There are no opportunities associated with this ID in the app");
        }
        return closedOpportunity;
    }

    @Override
    public OpportunityOutputDTO updateOpportunityCloseWon(int id) {


        OpportunityOutputDTO closedOpportunity = new OpportunityOutputDTO();
        opportunityRepository.updateOpportunityStatus(id,Status.CLOSED_WON);

        Optional<Opportunity> opportunity = opportunityRepository.findById(id);
        if (opportunity.isPresent()) {
            closedOpportunity.setId(opportunity.get().getId());
            closedOpportunity.setProduct(opportunity.get().getProduct());
            closedOpportunity.setQuantity(opportunity.get().getQuantity());
            closedOpportunity.setName(opportunity.get().getDecisionMaker().getName());
            closedOpportunity.setPhoneNumber(opportunity.get().getDecisionMaker().getPhoneNumber());
            closedOpportunity.setEmail(opportunity.get().getDecisionMaker().getEmail());
            closedOpportunity.setCompanyName(opportunity.get().getDecisionMaker().getCompanyName());
            closedOpportunity.setStatus(opportunity.get().getStatus());

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There are no opportunities associated with this ID in the app");
        }
        return closedOpportunity;
    }


}
