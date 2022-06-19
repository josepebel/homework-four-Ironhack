package com.ironhack.edgeservice.controller.interfaces;

import com.ironhack.edgeservice.controller.dto.SalesInputDTO;
import com.ironhack.edgeservice.controller.dto.SalesOutputDTO;

import java.util.List;

public interface SalesController {

    SalesOutputDTO createSales(SalesInputDTO salesInputDTO);
    List<SalesOutputDTO> showSales();
}
