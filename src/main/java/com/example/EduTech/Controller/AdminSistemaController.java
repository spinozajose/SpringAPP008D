package com.example.EduTech.Controller;

import com.example.EduTech.Model.Usuarios.AdminSistema;
import com.example.EduTech.Service.AdminSistemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin-sistema")
public class AdminSistemaController {

    @Autowired
    private AdminSistemaService service;

    @PostMapping("/crear")
    public AdminSistema crear(@RequestBody AdminSistema admin) {
        return service.crear(admin);
    }

    @PutMapping("/actualizar")
    public AdminSistema actualizar(@RequestBody AdminSistema admin) {
        return service.actualizar(admin);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        return service.eliminar(id) ? "Eliminado con éxito" : "No se encontró el administrador";
    }

    @GetMapping("/listar")
    public List<AdminSistema> listar() {
        return service.listar();
    }

    @GetMapping("/obtener/{id}")
    public AdminSistema obtenerPorId(@PathVariable int id) {
        return service.obtenerPorId(id);
    }

    @PutMapping("/desactivar/{id}")
    public String desactivar(@PathVariable int id) {
        return service.desactivar(id) ? "Administrador desactivado" : "No se encontró el administrador";
    }
}
