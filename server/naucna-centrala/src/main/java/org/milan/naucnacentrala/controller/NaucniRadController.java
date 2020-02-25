package org.milan.naucnacentrala.controller;

import com.sun.xml.internal.ws.api.pipe.ContentType;
import org.milan.naucnacentrala.service.NaucniRadService;
import org.milan.naucnacentrala.service_es.NaucniRadServiceES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping(value = "/api/naucni-radovi")
public class NaucniRadController {

    @Autowired
    NaucniRadService _nrService;

    @Autowired
    NaucniRadServiceES _nrServiceES;

    @GetMapping(path = "/es/{id}")
    public @ResponseBody
    ResponseEntity getNaucniRadES(@PathVariable int id) {
        return new ResponseEntity(_nrServiceES.getNaucniRadES(id), HttpStatus.OK);
    }


    @GetMapping(path = "/init")
    @PreAuthorize("hasRole('ROLE_AUTOR') or hasRole('ROLE_RECENZENT')")
    public @ResponseBody
    ResponseEntity initObjavaNaucnogRada(HttpServletRequest request) {
        _nrService.initObjavaNR(request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(path = "/file/{pid}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_AUTOR')")
    public @ResponseBody
    ResponseEntity uploadPDF(@RequestParam("file") MultipartFile file, @PathVariable String pid) throws IOException {
        _nrService.savePDF(file, pid);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/file/{pid}", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasRole('ROLE_AUTOR') or hasRole('ROLE_RECENZENT') or hasRole('ROLE_UREDNIK')")
    public @ResponseBody
    ResponseEntity downloadPDF(@PathVariable String pid) throws IOException {

        File filePdf = _nrService.getPDF(pid);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/pdf"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePdf.getName() + "\"")
                .body(new ByteArrayResource(Files.readAllBytes(filePdf.toPath())));
    }

    @GetMapping(path = "/file/nr/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody
    ResponseEntity downloadPDFNR(@PathVariable int id) throws IOException {

        File filePdf = _nrService.getPDF(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/pdf"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePdf.getName() + "\"")
                .body(new ByteArrayResource(Files.readAllBytes(filePdf.toPath())));
    }
}

