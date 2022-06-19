package com.ironhack.edgeservice.controller.interfaces;

import com.ironhack.edgeservice.controller.dto.OpportunityOutputDTO;

import java.util.List;

public interface ReportController {
    String reportClosedWonByCity();
    String reportCloseLostByCity();
    String reportOpenByCity();
    String reportClosedWonBySalesRep();
    String reportCloseLostBySalesRep();
    String reportOpenBySalesRep();
    String reportClosedWonByProduct();
    String reportCloseLostByProduct();
    String reportOpenByProduct();
    String reportClosedWonByCountry();
    String reportCloseLostByCountry();
    String reportOpenByCountry();
    String reportClosedWonByIndustry();
    String reportCloseLostByIndustry();
    String reportOpenByIndustry();
    String reportOpportunityByProduct();
    String reportOpportunityByCountry();
    String reportOpportunityByCity();
    String reportOpportunityIndustry();
    String reportOpportunityBySalesRep();
    String reportLeadBySalesRep();
    String reportMeanOppsAccount();
    String reportMedianOppsAccount();
    String reportMaxOppsAccount();
    String reportMinOppsAccount();


}
