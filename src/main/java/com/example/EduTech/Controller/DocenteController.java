package com.example.EduTech.Controller;

import com.example.EduTech.Model.Usuarios.Docente;
import com.example.EduTech.Service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/docentes")
public class DocenteController {

    @Autowired
    private DocenteService service;

    @PostMapping("/crear")
    public Docente crear(@RequestBody Docente docente) {
        return service.crear(docente);
    }

    @PutMapping("/actualizar")
    public Docente actualizar(@RequestBody Docente docente) {
        return service.actualizar(docente);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable int id) {
        service.eliminar(id);
    }

    @GetMapping("/listar")
    public List<Docente> listar() {
        return service.listar();
    }

    @GetMapping("/obtener/{id}")
    public String obtenerPorId(@PathVariable int id) {
        return service.obtenerPorId(id);
    }
}