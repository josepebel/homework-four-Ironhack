package com.ironhack.edgeservice.service.implementations;

import com.ironhack.edgeservice.client.OperativeServiceClient;
import com.ironhack.edgeservice.controller.dto.SalesInputDTO;
import com.ironhack.edgeservice.controller.dto.SalesOutputDTO;
import com.ironhack.edgeservice.enums.UserType;
import com.ironhack.edgeservice.model.Role;
import com.ironhack.edgeservice.model.User;
import com.ironhack.edgeservice.repository.RoleRepository;
import com.ironhack.edgeservice.repository.UserRepository;
import com.ironhack.edgeservice.service.interfaces.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private OperativeServiceClient operativeServiceClient;

    @Override
    public SalesOutputDTO createSales(SalesInputDTO salesInputDTO) {
        return operativeServiceClient.createSales(salesInputDTO);
    }

    @Override
    public List<SalesOutputDTO> showSales() {
        return operativeServiceClient.showSales();
    }
}
