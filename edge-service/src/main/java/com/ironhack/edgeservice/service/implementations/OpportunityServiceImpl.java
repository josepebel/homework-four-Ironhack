package com.ironhack.edgeservice.service.implementations;

import com.ironhack.edgeservice.client.OperativeServiceClient;
import com.ironhack.edgeservice.client.ReportingServiceClient;
import com.ironhack.edgeservice.controller.dto.OpportunityOutputDTO;
import com.ironhack.edgeservice.enums.Status;
import com.ironhack.edgeservice.model.Opportunity;
import com.ironhack.edgeservice.repository.OpportunityRepository;
import com.ironhack.edgeservice.service.interfaces.OpportunityService;
import feign.FeignException;
import feign.RetryableException;
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
    private OperativeServiceClient operativeServiceClient;

    @Autowired
    private ReportingServiceClient reportingServiceClient;

    @Override
    public List<OpportunityOutputDTO> showOpportunitiesByUserId(int id) {

        try {
            return operativeServiceClient.showOpportunitiesByUserId(id);
        } catch (RetryableException | FeignException.ServiceUnavailable e1) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Error in proxy service");
        } catch (FeignException.NotFound e2) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data found");
        }
    }

    @Override
    public OpportunityOutputDTO updateOpportunityCloseLost(int id) {
        return operativeServiceClient.updateOpportunityCloseLost(id);
    }

    @Override
    public OpportunityOutputDTO updateOpportunityCloseWon(int id) {
        return operativeServiceClient.updateOpportunityCloseWon(id);
    }

    public String meanQuantity() {
      return reportingServiceClient.meanQuantity();
    }

    public String medianQuantity() {
       return reportingServiceClient.medianQuantity();
    }

    public String maxQuantity() {
        return reportingServiceClient.maxQuantity();
    }

    public String minQuantity() {
       return reportingServiceClient.minQuantity();
    }
}
