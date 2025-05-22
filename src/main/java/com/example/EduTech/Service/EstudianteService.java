package com.example.EduTech.Service;

import com.example.EduTech.Model.Usuarios.Estudiante;
import com.example.EduTech.Repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository repository;

    public Estudiante crearEstudiante(Estudiante estudiante) {
        return repository.save(estudiante);
    }

    public Estudiante actualizarEstudiante(Estudiante estudiante) {
        return repository.save(estudiante);
    }

    public void eliminarEstudiante(int id) {
        repository.deleteById(id);
    }

    public List<Estudiante> listarEstudiantes() {
        return repository.findAll();
    }

    public Optional<Estudiante> obtenerPorId(int id) {
        return repository.findById(id);
    }

    public String obtenerNivelAcademico(int id) {
        Optional<Estudiante> estudiante = repository.findById(id);
        if (estudiante.isPresent()) {
            return estudiante.get().getNivelAcademico();
        } else {
            return "Estudiante no encontrado";
        }
    }

}
