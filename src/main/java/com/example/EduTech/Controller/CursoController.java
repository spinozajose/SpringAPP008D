package com.example.EduTech.Controller;

import com.example.EduTech.Model.Curso.Curso;
import com.example.EduTech.Service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService service;

    @PostMapping("/crear")
    public Curso crear(@RequestBody Curso curso) {
        return service.crearCurso(curso);
    }

    @PutMapping("/actualizar")
    public Curso actualizar(@RequestBody Curso curso) {
        return service.actualizarCurso(curso);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        service.eliminarCurso(id);
        return "Curso eliminado";
    }

    @GetMapping("/listar")
    public List<Curso> listar() {
        return service.listarCursos();
    }

    @GetMapping("/buscar/{id}")
    public Optional<Curso> obtenerPorId(@PathVariable int id) {
        return service.obtenerCursoPorId(id);
    }
}
