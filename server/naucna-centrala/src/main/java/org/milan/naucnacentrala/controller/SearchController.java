package org.milan.naucnacentrala.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.maps.errors.ApiException;
import org.elasticsearch.search.SearchHit;
import org.milan.naucnacentrala.search_es.dto.BooleanQueryDTO;
import org.milan.naucnacentrala.search_es.dto.QueryDTO;
import org.milan.naucnacentrala.service_es.NaucniRadServiceES;
import org.milan.naucnacentrala.service_es.UserServiceES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/search")
public class SearchController {

    @Autowired
    NaucniRadServiceES nrServiceES;

    @Autowired
    UserServiceES userServiceES;



    @PostMapping(path = "/simple/")
    public @ResponseBody
    ResponseEntity simpleSearch(@RequestBody QueryDTO qDTO) {
        return new ResponseEntity(nrServiceES.simpleSearch(qDTO), HttpStatus.OK);
    }



    @PostMapping(path = "/simple/highlight/")
    public @ResponseBody
    ResponseEntity simpleSearchHighlight(@RequestBody QueryDTO qDTO) throws JsonProcessingException {
        return new ResponseEntity(nrServiceES.simpleQueryHighlight(qDTO), HttpStatus.OK);
    }


    @PostMapping(path = "/bool/")
    public @ResponseBody
    ResponseEntity simpleSearchHighlight(@RequestBody BooleanQueryDTO bqDTO) throws IOException {
        return new ResponseEntity(nrServiceES.boolSearch(bqDTO), HttpStatus.OK);
    }


}
