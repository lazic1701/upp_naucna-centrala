package org.milan.naucnacentrala.controller;

import org.milan.naucnacentrala.model.dto.CasopisDTO;
import org.milan.naucnacentrala.model.dto.FinalizeOrderDTO;
import org.milan.naucnacentrala.model.dto.KupovinaClanarineDTO;
import org.milan.naucnacentrala.model.dto.NaucniRadDTO;
import org.milan.naucnacentrala.service.PorudzbinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/porudzbine")
public class PorudzbinaController {

    @Autowired
    PorudzbinaService _porudzbinaService;

    @GetMapping(path = "/")
    public @ResponseBody ResponseEntity getAllPorudzbine() {
        return new ResponseEntity(_porudzbinaService.getAllPorudzbine(), HttpStatus.OK);
    }

    @GetMapping(path = "/my")
    public @ResponseBody ResponseEntity getAllPorudzbine(HttpServletRequest request) {
        return new ResponseEntity(_porudzbinaService.getAllPorudzbineByUser(request), HttpStatus.OK);
    }


    @PostMapping(path = "/kupovina/casopis")
    public @ResponseBody ResponseEntity kupiCasopis(@RequestBody CasopisDTO cDTO, HttpServletRequest request) {
        return new ResponseEntity(_porudzbinaService.kupiCasopis(cDTO, request), HttpStatus.OK);
    }


    @PostMapping(path = "/kupovina/naucni-rad")
    public @ResponseBody ResponseEntity kupiNaucniRad(@RequestBody NaucniRadDTO nrDTO, HttpServletRequest request) {
        return new ResponseEntity(_porudzbinaService.kupiNaucniRad(nrDTO, request), HttpStatus.OK);
    }

    @PostMapping(path = "/kupovina/clanarina")
    public @ResponseBody ResponseEntity kupiClanarinu(@RequestBody KupovinaClanarineDTO kcDTO,
                                                      HttpServletRequest request) {
        return new ResponseEntity(_porudzbinaService
                .kupiClanarinu(kcDTO.getCasopis(), kcDTO.getPaymentType(), request), HttpStatus.OK);
    }

    @PostMapping(path = "/kupovina/finalize")
    public @ResponseBody ResponseEntity finalizeOrder(@RequestBody FinalizeOrderDTO foDTO) {
        _porudzbinaService.finalizeOrder(foDTO);
        return new ResponseEntity(HttpStatus.OK);
    }







}
