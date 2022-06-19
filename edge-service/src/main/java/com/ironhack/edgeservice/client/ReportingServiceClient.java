package com.ironhack.edgeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="reporting-service")
public interface ReportingServiceClient {

    @GetMapping("/report/closed-won-by-city")
    String reportClosedWonByCity();

    @GetMapping("/report/closed-lost-by-city")
    String reportCloseLostByCity();

    @GetMapping("/report/open-by-city")
    String reportOpenByCity();

    @GetMapping("/report/closed-won-by-sales")
    String reportClosedWonBySalesRep();

    @GetMapping("/report/closed-lost-by-sales")
    String reportCloseLostBySalesRep();

    @GetMapping("/report/open-by-sales")
    String reportOpenBySalesRep();

    @GetMapping("/report/closed-won-by-product")
    String reportClosedWonByProduct();

    @GetMapping("/report/closed-lost-by-product")
    String reportCloseLostByProduct();

    @GetMapping("/report/open-by-product")
    String reportOpenByProduct();

    @GetMapping("/report/closed-won-by-country")
    String reportClosedWonByCountry();

    @GetMapping("/report/closed-lost-by-country")
    String reportCloseLostByCountry();

    @GetMapping("/report/open-by-country")
    String reportOpenByCountry();

    @GetMapping("/report/closed-won-by-industry")
    String reportClosedWonByIndustry();

    @GetMapping("/report/closed-lost-by-industry")
    String reportCloseLostByIndustry();

    @GetMapping("/report/open-by-industry")
    String reportOpenByIndustry();

    @GetMapping("/report/opportunity-by-product")
    String reportOpportunityByProduct();

    @GetMapping("/report/opportunity-by-country")
    String reportOpportunityByCountry();

    @GetMapping("/report/opportunity-by-city")
    String reportOpportunityByCity();

    @GetMapping("/report/opportunity-by-industry")
    String reportOpportunityIndustry();

    @GetMapping("/report/opportunity-by-sales")
    String reportOpportunityBySalesRep();

    @GetMapping("/report/leads-by-sales")
    String reportLeadBySalesRep();

    @GetMapping("/report/mean-opps-account")
    String reportMeanOppsAccount();

    @GetMapping("/report/median-opps-account")
    String reportMedianOppsAccount();

    @GetMapping("/report/max-opps-account")
    String reportMaxOppsAccount();

    @GetMapping("/report/min-opps-account")
    String reportMinOppsAccount();

    @GetMapping("/employee-count/mean")
    String meanEmployeeCount();

    @GetMapping("/employee-count/median")
    String medianEmployeeCount();

    @GetMapping("employee-count/max")
    String maxEmployeeCount();

    @GetMapping("employee-count/min")
    String minEmployeeCount();

    @GetMapping("/opportunity-quantity/mean")
    String meanQuantity();

    @GetMapping("/opportunity-quantity/median")
    String medianQuantity();

    @GetMapping("/opportunity-quantity/max")
    String maxQuantity();

    @GetMapping("/opportunity-quantity/min")
    String minQuantity();
}
