package com.ironhack.edgeservice.controller.implementations;

import com.ironhack.edgeservice.controller.dto.ConvertInputDTO;
import com.ironhack.edgeservice.controller.interfaces.ConvertController;
import com.ironhack.edgeservice.security.CustomUserDetails;
import com.ironhack.edgeservice.service.interfaces.ConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class ConvertControllerImpl implements ConvertController {

    @Autowired
    private ConvertService convertService;

    @Override
    @PatchMapping("/convert/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void convertLead(@PathVariable int id, @RequestBody @Valid ConvertInputDTO convertInputDTO, @AuthenticationPrincipal CustomUserDetails user) {
        convertService.convertLead(id, convertInputDTO, user);
    }
}
