package com.ironhack.edgeservice.controller.implementations;

import com.ironhack.edgeservice.controller.interfaces.ReportController;
import com.ironhack.edgeservice.service.interfaces.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportControllerImpl implements ReportController {

    @Autowired
    private ReportService reportService;


    @Override
    @GetMapping("/report/closed-won-by-city")
    @ResponseStatus(HttpStatus.OK)
    public String reportClosedWonByCity() {
        return reportService.reportClosedWonByCity();
    }

    @Override
    @GetMapping("/report/closed-lost-by-city")
    @ResponseStatus(HttpStatus.OK)
    public String reportCloseLostByCity() {
        return reportService.reportCloseLostByCity();
    }

    @Override
    @GetMapping("/report/open-by-city")
    @ResponseStatus(HttpStatus.OK)
    public String reportOpenByCity() {
        return reportService.reportOpenByCity();
    }

    @Override
    @GetMapping("/report/closed-won-by-sales")
    @ResponseStatus(HttpStatus.OK)
    public String reportClosedWonBySalesRep() { return reportService.reportClosedWonBySalesRep(); }

    @Override
    @GetMapping("/report/closed-lost-by-sales")
    @ResponseStatus(HttpStatus.OK)
    public String reportCloseLostBySalesRep() {
        return reportService.reportCloseLostBySalesRep();
    }

    @Override
    @GetMapping("/report/open-by-sales")
    @ResponseStatus(HttpStatus.OK)
    public String reportOpenBySalesRep() { return reportService.reportOpenBySalesRep(); }

    @Override
    @GetMapping("/report/closed-won-by-product")
    @ResponseStatus(HttpStatus.OK)
    public String reportClosedWonByProduct() {
        return reportService.reportClosedWonByProduct();
    }

    @Override
    @GetMapping("/report/closed-lost-by-product")
    @ResponseStatus(HttpStatus.OK)
    public String reportCloseLostByProduct() {
        return reportService.reportCloseLostByProduct();
    }

    @Override
    @GetMapping("/report/open-by-product")
    @ResponseStatus(HttpStatus.OK)
    public String reportOpenByProduct() {
        return reportService.reportOpenByProduct();
    }

    @Override
    @GetMapping("/report/closed-won-by-country")
    @ResponseStatus(HttpStatus.OK)
    public String reportClosedWonByCountry() {
        return reportService.reportClosedWonByCountry();
    }

    @Override
    @GetMapping("/report/closed-lost-by-country")
    @ResponseStatus(HttpStatus.OK)
    public String reportCloseLostByCountry() {
        return reportService.reportCloseLostByCountry();
    }

    @Override
    @GetMapping("/report/open-by-country")
    @ResponseStatus(HttpStatus.OK)
    public String reportOpenByCountry() {
        return reportService.reportOpenByCountry();
    }

    @Override
    @GetMapping("/report/closed-won-by-industry")
    @ResponseStatus(HttpStatus.OK)
    public String reportClosedWonByIndustry() {
        return reportService.reportClosedWonByIndustry();
    }

    @Override
    @GetMapping("/report/closed-lost-by-industry")
    @ResponseStatus(HttpStatus.OK)
    public String reportCloseLostByIndustry() {
        return reportService.reportCloseLostByIndustry();
    }

    @Override
    @GetMapping("/report/open-by-industry")
    @ResponseStatus(HttpStatus.OK)
    public String reportOpenByIndustry() {
        return reportService.reportOpenByIndustry();
    }

    @Override
    @GetMapping("/report/opportunity-by-product")
    @ResponseStatus(HttpStatus.OK)
    public String reportOpportunityByProduct() { return reportService.reportOpportunityByProduct(); }

    @Override
    @GetMapping("/report/opportunity-by-country")
    @ResponseStatus(HttpStatus.OK)
    public String reportOpportunityByCountry() { return reportService.reportOpportunityByCountry(); }

    @Override
    @GetMapping("/report/opportunity-by-city")
    @ResponseStatus(HttpStatus.OK)
    public String reportOpportunityByCity() { return reportService.reportOpportunityByCity(); }

    @Override
    @GetMapping("/report/opportunity-by-industry")
    @ResponseStatus(HttpStatus.OK)
    public String reportOpportunityIndustry() { return reportService.reportOpportunityIndustry(); }

    @Override
    @GetMapping("/report/opportunity-by-sales")
    @ResponseStatus(HttpStatus.OK)
    public String reportOpportunityBySalesRep() { return reportService.reportOpportunityBySalesRep(); }

    @Override
    @GetMapping("/report/leads-by-sales")
    @ResponseStatus(HttpStatus.OK)
    public String reportLeadBySalesRep() { return reportService.reportLeadBySalesRep(); }


    @Override
    @GetMapping("/report/mean-opps-account")
    @ResponseStatus(HttpStatus.OK)
    public String reportMeanOppsAccount() {
        return reportService.reportMeanOppsAccount();
    }

    @Override
    @GetMapping("/report/median-opps-account")
    @ResponseStatus(HttpStatus.OK)
    public String reportMedianOppsAccount() {
        return reportService.reportMedianOppsAccount();
    }

    @Override
    @GetMapping("/report/max-opps-account")
    @ResponseStatus(HttpStatus.OK)
    public String reportMaxOppsAccount() {
        return reportService.reportMaxOppsAccount();
    }

    @Override
    @GetMapping("/report/min-opps-account")
    @ResponseStatus(HttpStatus.OK)
    public String reportMinOppsAccount() {
        return reportService.reportMinOppsAccount();
    }
}
