package com.hexora.hexora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ProyectosController {

    @GetMapping("/proyectos")
    public String listarProyectos() {
        return "proyectos";
    }
    
    @GetMapping("/proyectos/nuproyectos")
    public String crearProyecto() {
        return "nuproyectos";
    }

    @GetMapping("/proyectos/detalle")
    public String detalleProyecto() {
        return "detalle-proyecto";
    }
    
    
}
