package com.ironhack.edgeservice.service.implementations;

import com.ironhack.edgeservice.client.ReportingServiceClient;
import com.ironhack.edgeservice.enums.Status;
import com.ironhack.edgeservice.enums.UserType;
import com.ironhack.edgeservice.model.User;
import com.ironhack.edgeservice.repository.AccountRepository;
import com.ironhack.edgeservice.repository.LeadRepository;
import com.ironhack.edgeservice.repository.OpportunityRepository;
import com.ironhack.edgeservice.repository.UserRepository;
import com.ironhack.edgeservice.service.interfaces.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

   @Autowired
   private ReportingServiceClient reportingServiceClient;


    @Override
    public String reportClosedWonByCity() {
        return reportingServiceClient.reportClosedWonByCity();
    }

    @Override
    public String reportCloseLostByCity() {
        return reportingServiceClient.reportCloseLostByCity();
    }

    @Override
    public String reportOpenByCity() {
        return reportingServiceClient.reportOpenByCity();
    }

    @Override
    public String reportClosedWonBySalesRep() {
        return reportingServiceClient.reportClosedWonBySalesRep();
    }

    @Override
    public String reportCloseLostBySalesRep() {
        return reportingServiceClient.reportCloseLostBySalesRep();
    }

    @Override
    public String reportOpenBySalesRep() {

        return reportingServiceClient.reportOpenBySalesRep();
    }

    @Override
    public String reportClosedWonByProduct() {

        return reportingServiceClient.reportClosedWonByProduct();
    }

    @Override
    public String reportCloseLostByProduct() {

        return reportingServiceClient.reportCloseLostByProduct();
    }

    @Override
    public String reportOpenByProduct() {

        return reportingServiceClient.reportOpenByProduct();
    }

    @Override
    public String reportClosedWonByCountry() {

        return reportingServiceClient.reportClosedWonByCountry();
    }

    @Override
    public String reportCloseLostByCountry() {

        return reportingServiceClient.reportCloseLostByCountry();
    }

    @Override
    public String reportOpenByCountry() {

        return reportingServiceClient.reportOpenByCountry();
    }

    @Override
    public String reportClosedWonByIndustry() {

        return reportingServiceClient.reportClosedWonByIndustry();
    }

    @Override
    public String reportCloseLostByIndustry() {

        return reportingServiceClient.reportCloseLostByIndustry();
    }

    @Override
    public String reportOpenByIndustry() {

        return reportingServiceClient.reportOpenByIndustry();
    }

    @Override
    public String reportOpportunityByProduct() {

        return reportingServiceClient.reportOpportunityByProduct();
    }

    @Override
    public String reportOpportunityByCountry() {

        return reportingServiceClient.reportOpportunityByCountry();
    }

    @Override
    public String reportOpportunityByCity() {

        return reportingServiceClient.reportOpportunityByCity();
    }

    @Override
    public String reportOpportunityIndustry() {

        return reportingServiceClient.reportOpportunityIndustry();
    }

    @Override
    public String reportOpportunityBySalesRep() {

        return reportingServiceClient.reportOpportunityBySalesRep();
    }

    @Override
    public String reportLeadBySalesRep() {

        return reportingServiceClient.reportLeadBySalesRep();
    }

    @Override
    public String reportMeanOppsAccount() {

        return reportingServiceClient.reportMeanOppsAccount();
    }

    @Override
    public String reportMedianOppsAccount() {

        return reportingServiceClient.reportMedianOppsAccount();
    }

    @Override
    public String reportMaxOppsAccount() {

        return reportingServiceClient.reportMaxOppsAccount();
    }

    @Override
    public String reportMinOppsAccount() {

        return reportingServiceClient.reportMinOppsAccount();
    }
}
