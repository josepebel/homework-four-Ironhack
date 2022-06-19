package com.ironhack.edgeservice.service.interfaces;

import com.ironhack.edgeservice.controller.dto.SalesInputDTO;
import com.ironhack.edgeservice.controller.dto.SalesOutputDTO;

import java.util.List;

public interface SalesService {

    SalesOutputDTO createSales(SalesInputDTO salesInputDTO);
    List<SalesOutputDTO> showSales();
}
