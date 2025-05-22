package com.example.EduTech.Controller;

import com.example.EduTech.Model.Usuarios.GerenteCurso;
import com.example.EduTech.Service.GerenteCursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gerentecurso")
public class GerenteCursoController {

    @Autowired
    private GerenteCursoService service;

    @PostMapping("/crear")
    public GerenteCurso crear(@RequestBody GerenteCurso gerenteCurso) {
        return service.crear(gerenteCurso);
    }

    @PutMapping("/actualizar")
    public GerenteCurso actualizar(@RequestBody GerenteCurso gerenteCurso) {
        return service.actualizar(gerenteCurso);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        return service.eliminar(id) ? "Gerente de Curso eliminado con éxito" : "No se encontró el gerente";
    }

    @GetMapping("/listar")
    public List<GerenteCurso> listar() {
        return service.listar();
    }

    @GetMapping("/obtener/{id}")
    public GerenteCurso obtenerPorId(@PathVariable int id) {
        return service.obtenerPorId(id);
    }

    @PutMapping("/desactivar/{id}")
    public String desactivar(@PathVariable int id) {
        return service.desactivar(id) ? "Gerente de Curso desactivado" : "No se encontró el gerente";
    }
}
