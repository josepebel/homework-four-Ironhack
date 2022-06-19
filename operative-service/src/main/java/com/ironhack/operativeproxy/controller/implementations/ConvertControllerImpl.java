package com.ironhack.operativeproxy.controller.implementations;

import com.ironhack.operativeproxy.controller.dto.ConvertInputDTO;
import com.ironhack.operativeproxy.controller.interfaces.ConvertController;
import com.ironhack.operativeproxy.service.interfaces.ConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ConvertControllerImpl implements ConvertController {

    @Autowired
    private ConvertService convertService;

    @Override
    @PatchMapping("/convert/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void convertLead(@PathVariable int id, @RequestBody @Valid ConvertInputDTO convertInputDTO, @RequestParam int userId) {
        convertService.convertLead(id, convertInputDTO, userId);
    }
}
