package org.milan.naucnacentrala.controller;

import org.milan.naucnacentrala.model.dto.CasopisDTO;
import org.milan.naucnacentrala.model.dto.KPRegistrationDTO;
import org.milan.naucnacentrala.model.dto.TaskDTO;
import org.milan.naucnacentrala.service.CasopisService;
import org.milan.naucnacentrala.service.KPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/casopisi")
public class CasopisController {

    @Autowired
    CasopisService _casopisService;

    @Autowired
    KPService _kpService;

    @GetMapping(path = "/init")
    @PreAuthorize("hasRole('ROLE_UREDNIK')")
    public @ResponseBody
    ResponseEntity initKreiranjeCasopisa() {
        _casopisService.initProcessKreiranjeCasopisa();
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public @ResponseBody
    ResponseEntity getAllCasopisi() {
        return new ResponseEntity(_casopisService.getAllCasopisi(), HttpStatus.OK);
    }

    @GetMapping(path = "/glavni-urednik/{glavniUrednikId}")
    public @ResponseBody
    ResponseEntity getAllGlavniUrednikCasopisi(@PathVariable int glavniUrednikId) {
        return new ResponseEntity(_casopisService.getAllGlavniUrednikCasopisi(glavniUrednikId), HttpStatus.OK);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity registerSeller(@RequestBody CasopisDTO casopisDTO){
        return new ResponseEntity<>(_kpService.initRegistration(casopisDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/registration/review", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity reviewRegistrationSeller(@RequestBody CasopisDTO casopisDTO){
        return new ResponseEntity<>(_kpService.reviewRegistrationSeller(casopisDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/registration/status", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity registerSeller(@RequestBody KPRegistrationDTO kprDTO) {
        _kpService.changeRegistrationStatus(kprDTO);
        return new ResponseEntity(HttpStatus.OK);
    }





}
