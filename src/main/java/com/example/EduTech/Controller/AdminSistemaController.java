package com.example.EduTech.Controller;

import com.example.EduTech.Service.AdminSistemaService;
import com.example.EduTech.Model.AdminSistema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminSistemaController {

    @Autowired
    private AdminSistemaService service;

    @PostMapping
    public String crear(@RequestBody AdminSistema admin) {
        return service.crear(admin);
    }

    @PutMapping
    public String actualizar(@RequestBody AdminSistema admin) {
        return service.actualizar(admin);
    }

    @PutMapping("/desactivar/{id}")
    public String desactivar(@PathVariable int id) {
        return service.desactivar(id);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        return service.eliminar(id);
    }

    @GetMapping
    public List<AdminSistema> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public AdminSistema obtenerPorId(@PathVariable int id) {
        return service.obtenerPorId(id);
    }
}
