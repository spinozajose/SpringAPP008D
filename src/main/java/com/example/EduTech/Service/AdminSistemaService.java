package com.example.EduTech.Service;

import com.example.EduTech.Model.Usuarios.AdminSistema;
import com.example.EduTech.Repository.AdminSistemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminSistemaService {

    @Autowired
    AdminSistemaRepository repository;

    public String crear(AdminSistema admin) {
        return repository.agregarAdministrador(admin);
    }

    public String actualizar(AdminSistema admin) {
        return repository.actualizarAdministrador(admin);
    }

    public String desactivar(int id) {
        return repository.desactivarAdministrador(id);
    }

    public String eliminar(int id) {
        return repository.eliminarAdministrador(id);
    }

    public List<AdminSistema> listar() {
        return repository.obtenerTodos();
    }

    public String obtenerPorId(int id) {
        return repository.obtenerPorId(id);
    }
}
