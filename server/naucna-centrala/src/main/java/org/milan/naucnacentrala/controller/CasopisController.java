package org.milan.naucnacentrala.controller;

import org.milan.naucnacentrala.model.dto.TaskDTO;
import org.milan.naucnacentrala.service.CasopisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/casopisi")
public class CasopisController {

    @Autowired
    CasopisService _casopisService;

    @GetMapping(path = "/init")
    @PreAuthorize("hasRole('ROLE_UREDNIK')")
    public @ResponseBody
    ResponseEntity initKreiranjeCasopisa() {
        _casopisService.initProcessKreiranjeCasopisa();
        return new ResponseEntity(HttpStatus.OK);
    }





}
