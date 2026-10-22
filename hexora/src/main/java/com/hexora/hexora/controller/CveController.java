package com.hexora.hexora.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexora.hexora.model.Cve;
import com.hexora.hexora.service.CveService;

/**
 * Ventanilla de la app para buscar CVEs.
 * Ejemplo: http://localhost:8080/api/cves/buscar?q=CVE-2021-44228
 */
@RestController
@RequestMapping("/api/cves")
public class CveController {

    private final CveService cveService;

    public CveController(CveService cveService) {
        this.cveService = cveService;
    }

    @GetMapping("/buscar")
    public List<Cve> buscar(@RequestParam("q") String q) {
        return cveService.buscar(q);
    }
}
