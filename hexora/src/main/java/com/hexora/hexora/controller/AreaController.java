package com.hexora.hexora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AreaController {

    @GetMapping("/areas/nueva")
    public String crearArea() {
        return "crear-area";
    }

    @GetMapping("/areas/detalle")
    public String detalleArea() {
        return "detalle-area";
    }
    
    
}
