package com.example.EduTech.Service;

import com.example.EduTech.Model.Curso.Curso;
import com.example.EduTech.Repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository repository;

    public Curso crearCurso(Curso curso) {
        return repository.save(curso);
    }

    public Curso actualizarCurso(Curso curso) {
        return repository.save(curso);
    }

    public void eliminarCurso(int id) {
        repository.deleteById(id);
    }

    public List<Curso> listarCursos() {
        return repository.findAll();
    }

    public Optional<Curso> obtenerCursoPorId(int id) {
        return repository.findById(id);
    }
}
