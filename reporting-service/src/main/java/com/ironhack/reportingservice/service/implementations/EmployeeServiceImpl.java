package com.ironhack.reportingservice.service.implementations;

import com.ironhack.reportingservice.repository.AccountRepository;
import com.ironhack.reportingservice.service.interfaces.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private AccountRepository accountRepository;

    public String meanEmployeeCount() {
        if (accountRepository.meanEmployeeCount() == null) {
            return "Mean Employee Count is: 0";
        } else {
            return "Mean Employee Count is: " + accountRepository.meanEmployeeCount();
        }
    }

    public String medianEmployeeCount() {
        if (accountRepository.medianEmployeeCount() == null) {
            return "Median Employee Count is: 0";
        } else {
            return "Median Employee Count is: " + accountRepository.medianEmployeeCount();
        }
    }

    public String maxEmployeeCount() {
        if (accountRepository.maxEmployeeCount().isEmpty()) {
            return "Max Employee Count is: 0";
        } else {
            return "Max Employee Count is: " + accountRepository.maxEmployeeCount().get(0)[1] + ", From account: " +
                    accountRepository.maxEmployeeCount().get(0)[0];
        }
    }

    public String minEmployeeCount() {
        if (accountRepository.minEmployeeCount().isEmpty()) {
            return "Min Employee Count is: 0";
        } else {
            return "Min Employee Count is: " + accountRepository.minEmployeeCount().get(0)[1] + ", From account: " +
                    accountRepository.minEmployeeCount().get(0)[0];
        }
    }
}
