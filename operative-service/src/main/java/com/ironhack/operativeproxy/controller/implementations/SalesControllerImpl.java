package com.ironhack.operativeproxy.controller.implementations;

import com.ironhack.operativeproxy.controller.dto.SalesInputDTO;
import com.ironhack.operativeproxy.controller.dto.SalesOutputDTO;
import com.ironhack.operativeproxy.controller.interfaces.SalesController;
import com.ironhack.operativeproxy.service.interfaces.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SalesControllerImpl implements SalesController {

    @Autowired
    private SalesService salesService;

    @Override
    @PostMapping("/new-sales")
    @ResponseStatus(HttpStatus.CREATED)
    public SalesOutputDTO createSales(@RequestBody @Valid SalesInputDTO salesInputDTO) {
        return salesService.createSales(salesInputDTO);
    }

    @Override
    @GetMapping("/show-sales")
    @ResponseStatus(HttpStatus.OK)
    public List<SalesOutputDTO> showSales() {
        return salesService.showSales();
    }
}
