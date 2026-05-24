package com.hexora.hexora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ProyectosController {

    @GetMapping("/proyectos")
    public String proyectos() {
        return "proyectos";
    }

    @GetMapping("/proyectos/nuproyectos")
    public String crearProyecto() {
        return "nuproyectos";
    }

    @PostMapping("/proyectos/guardar")
    public String guardarProyecto() {
        return "redirect:/proyectos";
    }

}

/* 
@Controller
@RequestMapping("/proyectos")
public class ProyectosController {

    //private final AuditoriaRepository auditoriaRepository;
    //private final EmpresaRepository empresaRepository;

    
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
        @RequestParam String tipoAuditoria,
        @RequestParam String fechaInicio,
        @RequestParam (required=false)String fechaFin,
        @RequestParam String estado
    ) {
        Empresa empresa = new Empresa();
        empresa.setNombre(nombreEmpresa);
        empresa.setNit(nit);
        empresa.setSector(sector);
        empresaRepository.save(empresa);
        
        Auditoria auditoria = new Auditoria();
        auditoria.setNombre(nombre);
        auditoria.setTipoAuditoria(tipoAuditoria);
        auditoria.setFechaInico(LocalDate.parse(fechaInicio));

        if (fechaFin != null && !fechaFin.isEmpty()) {
            auditoria.setFechaFin(LocalDate.parse(fechaFin));
        }

        auditoria.setEstado(estado);
        auditoria.setEmpresa(empresa);
        auditoriaRepository.save(auditoria);
        
        return "redirect:/proyectos";
    }
    
    @GetMapping("/proyectos/{id}")
    public String detalleProyecto(@PathVariable Long id, Model model) {
        Auditoria auditoria = auditoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Auditoría no encontrada"));
        model.addAttribute("auditoria", auditoria);
        return "detalle-proyecto";
    }
    
    @GetMapping("/{id}/editar")
    public String proyectoEditar(@PathVariable Long id, Model model) {

        Auditoria auditoria = auditoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Auditoría no encontrada"));

        model.addAttribute("auditoria", auditoria);

        return "editar-auditoria";
    }

    @PostMapping("/{id}/actualizar")
    public String proyectoActualizar(
        @PathVariable Long id, 
        @RequestParam String nombreEmpresa,
        @RequestParam(required=false) String nit,
        @RequestParam(required = false) String sector,

        @RequestParam String nombre,
        @RequestParam String tipoAuditoria,
        @RequestParam String fechaInicio,
        @RequestParam (required=false)String fechaFin,
        @RequestParam String estado
    ) {
        Auditoria auditoria = auditoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        Empresa empresa = auditoria.getEmpresa();

        empresa.setNombre(nombreEmpresa);
        empresa.setNit(nit);
        empresa.setSector(sector);
        empresaRepository.save(empresa);

        auditoria.setNombre(nombre);
        auditoria.setTipoAuditoria(tipoAuditoria);
        auditoria.setFechaInico(LocalDate.parse(fechaInicio));

        if (fechaFin != null && !fechaFin.isEmpty()) {
            auditoria.setFechaFin(LocalDate.parse(fechaFin));
        } else{
            auditoria.setFechaFin(null);
        }

        auditoria.setEstado(estado);

        auditoriaRepository.save(auditoria);

        return "redirect:/proyectos/" + id;
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarProyecto(@PathVariable Long id) {
        auditoriaRepository.deleteById(id);
        return "redirect:/proyectos";
    }
    
}
*/