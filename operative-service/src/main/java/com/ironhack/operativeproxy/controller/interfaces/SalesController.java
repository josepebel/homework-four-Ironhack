package com.ironhack.operativeproxy.controller.interfaces;

import com.ironhack.operativeproxy.controller.dto.SalesInputDTO;
import com.ironhack.operativeproxy.controller.dto.SalesOutputDTO;

import java.util.List;

public interface SalesController {

    SalesOutputDTO createSales(SalesInputDTO salesInputDTO);
    List<SalesOutputDTO> showSales();
}
