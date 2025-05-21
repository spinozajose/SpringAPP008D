package com.example.EduTech.Repository;

import com.example.EduTech.Model.AdminSistema;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminSistemaRepository {

    private List<AdminSistema> administradores = new ArrayList<>();

    public String agregarAdministrador(AdminSistema admin) {
        administradores.add(admin);
        return "Administrador agregado con éxito";
    }

    public String actualizarAdministrador(AdminSistema nuevoAdmin) {
        for (AdminSistema admin : administradores) {
            if (admin.getId() == nuevoAdmin.getId()) {
                int index = administradores.indexOf(admin);
                administradores.set(index, nuevoAdmin);
                return "Administrador actualizado con éxito";
            }
        }
        return "Administrador no encontrado";
    }

    public String desactivarAdministrador(int id) {
        for (AdminSistema admin : administradores) {
            if (admin.getId() == id) {
                admin.setActivo(false);
                return "Administrador desactivado con éxito";
            }
        }
        return "Administrador no encontrado";
    }

    public String eliminarAdministrador(int id) {
        for (AdminSistema admin : administradores) {
            if (admin.getId() == id) {
                administradores.remove(admin);
                return "Administrador eliminado con éxito";
            }
        }
        return "Administrador no encontrado";
    }

    public List<AdminSistema> obtenerTodos() {
        return administradores;
    }

    public AdminSistema obtenerPorId(int id) {
        return administradores.stream()
                .filter(admin -> admin.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
