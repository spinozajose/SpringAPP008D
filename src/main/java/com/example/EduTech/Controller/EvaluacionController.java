package com.example.EduTech.Controller;

import com.example.EduTech.Model.Curso.Evaluacion;
import com.example.EduTech.Service.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/evaluaciones")
public class EvaluacionController {

    @Autowired
    private EvaluacionService service;

    @GetMapping("/prueba")
    public String testApi() {
        return "API funcionando";
    }


    @PostMapping("/crear")
    public Evaluacion crear(@RequestBody Evaluacion evaluacion) {
        return service.crearEvaluacion(evaluacion);
    }

    @PutMapping("/actualizar")
    public Evaluacion actualizar(@RequestBody Evaluacion evaluacion) {
        return service.actualizarEvaluacion(evaluacion);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        service.eliminarEvaluacion(id);
        return "Evaluación eliminada";
    }

    @GetMapping("/listar")
    public List<Evaluacion> listar() {
        return service.listarEvaluaciones();
    }

    @GetMapping("/buscar/{id}")
    public Optional<Evaluacion> obtenerPorId(@PathVariable int id) {
        return service.obtenerEvaluacionPorId(id);
    }
}
