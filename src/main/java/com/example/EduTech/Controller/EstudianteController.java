package com.example.EduTech.Controller;

import com.example.EduTech.Model.Usuarios.Estudiante;
import com.example.EduTech.Service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService service;

    @PostMapping("/crear")
    public Estudiante crear(@RequestBody Estudiante estudiante) {
        return service.crearEstudiante(estudiante);
    }

    @PutMapping("/actualizar")
    public Estudiante actualizar(@RequestBody Estudiante estudiante) {
        return service.actualizarEstudiante(estudiante);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        service.eliminarEstudiante(id);
        return "Estudiante eliminado";
    }

    @GetMapping("/listar")
    public List<Estudiante> listar() {
        return service.listarEstudiantes();
    }

    @GetMapping("/buscar/{id}")
    public Optional<Estudiante> obtenerPorId(@PathVariable int id) {
        return service.obtenerPorId(id);
    }

    @GetMapping("/nivel-academico/{id}")
    public String obtenerNivelAcademico(@PathVariable int id) {
        return service.obtenerNivelAcademico(id);
    }

}
