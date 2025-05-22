package com.example.EduTech.Controller;

import com.example.EduTech.Model.Usuarios.LogisticaSoporte;
import com.example.EduTech.Service.LogisticaSoporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/logistica")
@CrossOrigin(origins = "*") // Puedes limitarlo si es necesario
public class LogisticaSoporteController {

    @Autowired
    private LogisticaSoporteService servicio;

    @GetMapping("/listar")
    public List<LogisticaSoporte> listarTodos() {
        return servicio.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<LogisticaSoporte> obtenerPorId(@PathVariable int id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping("/crear")
    public LogisticaSoporte crear(@RequestBody LogisticaSoporte logistica) {
        return servicio.crear(logistica);
    }

    @PutMapping("/{id}")
    public LogisticaSoporte actualizar(@PathVariable int id, @RequestBody LogisticaSoporte logistica) {
        return servicio.actualizar(id, logistica);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable int id) {
        servicio.eliminar(id);
    }
    @GetMapping("/prueba")
    public String prueba() {
        return "API de estudiantes activa";
    }
}
