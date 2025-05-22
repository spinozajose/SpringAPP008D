package com.example.EduTech.Service;

import com.example.EduTech.Model.Usuarios.LogisticaSoporte;
import com.example.EduTech.Repository.LogisticaSoporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogisticaSoporteService {

    @Autowired
    private LogisticaSoporteRepository logisticaRepo;

    public List<LogisticaSoporte> obtenerTodos() {
        return logisticaRepo.findAll();
    }

    public Optional<LogisticaSoporte> obtenerPorId(int id) {
        return logisticaRepo.findById(id);
    }

    public LogisticaSoporte crear(LogisticaSoporte logistica) {
        return logisticaRepo.save(logistica);
    }

    public LogisticaSoporte actualizar(int id, LogisticaSoporte datosActualizados) {
        return logisticaRepo.findById(id).map(existing -> {
            existing.setNombreUsuario(datosActualizados.getNombreUsuario());
            existing.setContrasena(datosActualizados.getContrasena());
            existing.setCorreo(datosActualizados.getCorreo());
            existing.setRol(datosActualizados.getRol());
            existing.setAreaResponsable(datosActualizados.getAreaResponsable());
            existing.setActivo(datosActualizados.isActivo());
            return logisticaRepo.save(existing);
        }).orElse(null);
    }

    public void eliminar(int id) {
        logisticaRepo.deleteById(id);
    }
}
