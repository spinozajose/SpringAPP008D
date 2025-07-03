package com.example.EduTech.Service;

import com.example.EduTech.Model.Curso.Evaluacion;
import com.example.EduTech.Repository.EvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluacionService {

    @Autowired
    private EvaluacionRepository repository;

    public Evaluacion crearEvaluacion(Evaluacion evaluacion) {
        validarNota(evaluacion.getNota());
        return repository.save(evaluacion);
    }


    public Evaluacion actualizarEvaluacion(Evaluacion evaluacion) {
        validarNota(evaluacion.getNota());
        return repository.save(evaluacion);
    }

    private void validarNota(double nota) {
        if (nota < 0.0 || nota > 7.0) {
            throw new IllegalArgumentException("La nota debe estar entre 0.0 y 7.0");
        }
    }
    public void eliminarEvaluacion(int id) {
        repository.deleteById(id);
    }

    public List<Evaluacion> listarEvaluaciones() {
        return repository.findAll();
    }

    public Optional<Evaluacion> obtenerEvaluacionPorId(int id) {
        return repository.findById(id);
    }
}
