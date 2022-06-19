package com.ironhack.operativeproxy.service.implementations;

import com.ironhack.operativeproxy.controller.dto.SalesInputDTO;
import com.ironhack.operativeproxy.controller.dto.SalesOutputDTO;
import com.ironhack.operativeproxy.enums.UserType;
import com.ironhack.operativeproxy.model.Role;
import com.ironhack.operativeproxy.model.User;
import com.ironhack.operativeproxy.repository.RoleRepository;
import com.ironhack.operativeproxy.repository.UserRepository;
import com.ironhack.operativeproxy.service.interfaces.SalesService;
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
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public SalesOutputDTO createSales(SalesInputDTO salesInputDTO) {

        User sales = new User(salesInputDTO.getName(), UserType.SALESREPRESENTATIVE, salesInputDTO.getPassword(), "Active",
                new Date(), new Date(0L), salesInputDTO.getName(), "");
        userRepository.save(sales);

        SalesOutputDTO salesOutputDTO = new SalesOutputDTO();
        salesOutputDTO.setId(sales.getId());
        salesOutputDTO.setName(sales.getName());
        salesOutputDTO.setUserType(sales.getUserType());
        salesOutputDTO.setPasswordAccess("******");
        salesOutputDTO.setStatus(sales.getStatus());
        salesOutputDTO.setCreationDate(sales.getCreationDate());
        salesOutputDTO.setUserCreation(sales.getUserCreation());

        Role roleSalesRepresentative = new Role();
        roleSalesRepresentative.setName("SALESREPRESENTATIVE");
        roleSalesRepresentative.setUser(sales);
        roleRepository.save(roleSalesRepresentative);

        return salesOutputDTO;
    }

    @Override
    public List<SalesOutputDTO> showSales() {

        List<User> salesList = userRepository.getAllUserByUserType(UserType.SALESREPRESENTATIVE);

        if (salesList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Sales in application found");
        }
        List<SalesOutputDTO> outputDTOList = new ArrayList<>();
        for (User user : salesList) {
            SalesOutputDTO salesOutputDTO = new SalesOutputDTO();
            salesOutputDTO.setId(user.getId());
            salesOutputDTO.setName(user.getName());
            salesOutputDTO.setUserType(user.getUserType());
            salesOutputDTO.setPasswordAccess("******");
            salesOutputDTO.setStatus(user.getStatus());
            salesOutputDTO.setCreationDate(user.getCreationDate());
            salesOutputDTO.setUserCreation(user.getUserCreation());
            outputDTOList.add(salesOutputDTO);
        }
        return outputDTOList;
    }
}
