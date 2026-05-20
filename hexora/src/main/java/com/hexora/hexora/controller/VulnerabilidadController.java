package com.hexora.hexora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class VulnerabilidadController {

    @GetMapping("/vulnerabilidades/nueva")
    public String crearVulnerabilidad() {
        return "crear-vulnerabilidad";
    }
    
    @GetMapping("/vulnerabilidades/detalle")
    public String detalleVulnerabilidad() {
        return "detalle-vulnerabilidad";
    }

    @GetMapping("/vulnerabilidades/editar")
    public String editarVulnerabilidad() {
        return "editar-vulnerabilidad";
    }
    
}
