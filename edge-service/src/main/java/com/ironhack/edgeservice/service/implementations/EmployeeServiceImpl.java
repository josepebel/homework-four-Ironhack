package com.ironhack.edgeservice.service.implementations;

import com.ironhack.edgeservice.client.OperativeServiceClient;
import com.ironhack.edgeservice.client.ReportingServiceClient;
import com.ironhack.edgeservice.repository.AccountRepository;
import com.ironhack.edgeservice.service.interfaces.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private ReportingServiceClient reportingServiceClient;

    public String meanEmployeeCount() {
      return reportingServiceClient.meanEmployeeCount();
    }

    public String medianEmployeeCount() {
       return reportingServiceClient.medianEmployeeCount();
    }

    public String maxEmployeeCount() {
       return reportingServiceClient.maxEmployeeCount();
    }

    public String minEmployeeCount() {
     return reportingServiceClient.minEmployeeCount();
    }
}
