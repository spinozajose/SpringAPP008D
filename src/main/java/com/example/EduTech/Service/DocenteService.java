package com.example.EduTech.Service;

import com.example.EduTech.Model.Usuarios.Docente;
import com.example.EduTech.Repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocenteService {

    @Autowired
    private DocenteRepository repository;

    public Docente crear(Docente docente) {
        return repository.save(docente);
    }

    public Docente actualizar(Docente docente) {
        return repository.save(docente);
    }

    public void eliminar(int id) {
        repository.deleteById(id);
    }

    public List<Docente> listar() {
        return repository.findAll();
    }

    public String obtenerPorId(int id) {
        Optional<Docente> docenteOpt = repository.findById(id);
        if (docenteOpt.isPresent()) {
            Docente docente = docenteOpt.get();
            return "ID: " + docente.getId() + "\n"
                    + "Nombre: " + docente.getNombreUsuario() + "\n"
                    + "Correo: " + docente.getCorreo() + "\n"
                    + "Rol: " + docente.getRol() + "\n"
                    + "Especialidad: " + docente.getEspecialidad() + "\n"
                    + "Activo: " + docente.isActivo();
        } else {
            return "No se encontró el Docente";
        }
    }
}