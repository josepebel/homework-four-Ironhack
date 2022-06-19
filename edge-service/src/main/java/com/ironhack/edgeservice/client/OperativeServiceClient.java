package com.ironhack.edgeservice.client;

import com.ironhack.edgeservice.controller.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="operative-service")
public interface OperativeServiceClient {

    @PatchMapping("/convert/{id}")
    void convertLead(@PathVariable int id, @RequestBody ConvertInputDTO convertInputDTO, @RequestParam int userId);

    @PostMapping("/new-lead")
    LeadOutputDTO createLead(@RequestBody LeadInputDTO leadsInputDTO, @RequestParam int userId);

    @GetMapping("/show-leads")
    List<LeadOutputDTO> showLeads();

    @GetMapping("/lead/{id}")
    LeadOutputDTO getLead(@PathVariable Integer id);

    @GetMapping("/show-opportunities/{id}")
    List<OpportunityOutputDTO> showOpportunitiesByUserId(@PathVariable int id);

    @PutMapping("/close-lost-opportunity/{id}")
    OpportunityOutputDTO updateOpportunityCloseLost(@PathVariable int id);

    @PutMapping("/close-won-opportunity/{id}")
    OpportunityOutputDTO updateOpportunityCloseWon(@PathVariable int id);

    @PostMapping("/new-sales")
    SalesOutputDTO createSales(SalesInputDTO salesInputDTO);

    @GetMapping("/show-sales")
    List<SalesOutputDTO> showSales();


}

