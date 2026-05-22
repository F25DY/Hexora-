package com.hexora.hexora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hexora.hexora.repository.AuditoriaRepository;
import com.hexora.hexora.repository.EmpresaRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/proyectos")
public class ProyectosController {

    private final AuditoriaRepository auditoriaRepository;
    private final EmpresaRepository empresaRepository;

    
    public ProyectosController(AuditoriaRepository auditoriaRepository, EmpresaRepository empresaRepository) {
        this.auditoriaRepository = auditoriaRepository;
        this.empresaRepository = empresaRepository;
    }

    @GetMapping("/proyectos")
    public String listarProyectos(Model model) {
        model.addAttribute("auditorias", auditoriaRepository.findAll());
        model.addAttribute("empresas", empresaRepository.findAll());
        return "proyectos";
    }
    
    @GetMapping("/proyectos/nuproyectos")
    public String crearProyecto() {
        return "nuproyectos";
    }

    @PostMapping("/guardar")
    public String guardarProyectos(
        @RequestParam String nombreEmpresa,
        @RequestParam(required=false) String nit,
        @RequestParam(required = false) String sector,

        @RequestParam String nombre,
        @RequestParam(required = false) String descripcion,
        @RequestParam String tipoAuditoria,
        @RequestParam String fechaInicio,
        @RequestParam (required=false)String fechaFin,
        @RequestParam String estado
    ) {
        //TODO: process POST request
        
        return entity;
    }
    
    @GetMapping("/proyectos/{id}")
    public String detalleProyecto() {
        return "detalle-proyecto";
    }
    
    
}
