package org.milan.naucnacentrala.controller;

import org.milan.naucnacentrala.search_es.dto.QueryDTO;
import org.milan.naucnacentrala.service_es.NaucniRadServiceES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/search")
public class SearchController {

    @Autowired
    NaucniRadServiceES nrServiceES;

    @PostMapping(path = "/simple/")
    public @ResponseBody
    ResponseEntity simpleSearch(@RequestBody QueryDTO qDTO) {
        return new ResponseEntity(nrServiceES.simpleQuery(qDTO), HttpStatus.OK);
    }
}
