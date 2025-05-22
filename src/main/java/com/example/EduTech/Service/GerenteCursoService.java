package com.example.EduTech.Service;

import com.example.EduTech.Model.Usuarios.GerenteCurso;
import com.example.EduTech.Repository.GerenteCursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GerenteCursoService {

    @Autowired
    private GerenteCursoRepository repository;

    public GerenteCurso crear(GerenteCurso gerenteCurso) {
        return repository.save(gerenteCurso);
    }

    public GerenteCurso actualizar(GerenteCurso gerenteCurso) {
        return repository.save(gerenteCurso);
    }

    public boolean eliminar(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public GerenteCurso obtenerPorId(int id) {
        Optional<GerenteCurso> gerente = repository.findById(id);
        return gerente.orElse(null);
    }

    public List<GerenteCurso> listar() {
        return repository.findAll();
    }

    public boolean desactivar(int id) {
        Optional<GerenteCurso> gerenteOpt = repository.findById(id);
        if (gerenteOpt.isPresent()) {
            GerenteCurso gerente = gerenteOpt.get();
            gerente.setActivo(false);
            repository.save(gerente);
            return true;
        }
        return false;
    }

    // Aquí podria implementar los metodos como:
    // generar reportes, asignar instructores, evaluar contenido. Pero habria que hacerlos de forma independiente como subclases
}
