package com.ironhack.operativeproxy.service.interfaces;

import com.ironhack.operativeproxy.controller.dto.SalesInputDTO;
import com.ironhack.operativeproxy.controller.dto.SalesOutputDTO;

import java.util.List;

public interface SalesService {

    SalesOutputDTO createSales(SalesInputDTO salesInputDTO);
    List<SalesOutputDTO> showSales();
}
