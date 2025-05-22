package com.example.EduTech.Service;

import com.example.EduTech.Model.Usuarios.AdminSistema;
import com.example.EduTech.Repository.AdminSistemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminSistemaService {

    @Autowired
    private AdminSistemaRepository repository;

    public AdminSistema crear(AdminSistema admin) {
        return repository.save(admin);
    }

    public AdminSistema actualizar(AdminSistema admin) {
        return repository.save(admin);
    }

    public boolean eliminar(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public AdminSistema obtenerPorId(int id) {
        Optional<AdminSistema> admin = repository.findById(id);
        return admin.orElse(null);
    }

    public List<AdminSistema> listar() {
        return repository.findAll();
    }

    public boolean desactivar(int id) {
        Optional<AdminSistema> adminOpt = repository.findById(id);
        if (adminOpt.isPresent()) {
            AdminSistema admin = adminOpt.get();
            admin.setActivo(false);
            repository.save(admin);
            return true;
        }
        return false;
    }
}
