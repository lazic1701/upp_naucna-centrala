package org.milan.naucnacentrala.controller;

import org.milan.naucnacentrala.service.NaucniRadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/naucni-radovi")
public class NaucniRadController {

    @Autowired
    NaucniRadService _nrService;

    @GetMapping(path = "/init")
    @PreAuthorize("hasRole('ROLE_AUTOR') or hasRole('ROLE_RECENZENT')")
    public @ResponseBody
    ResponseEntity initObjavaNaucnogRada(HttpServletRequest request) {
        _nrService.initObjavaNR(request);
        return new ResponseEntity(HttpStatus.OK);
    }
}
