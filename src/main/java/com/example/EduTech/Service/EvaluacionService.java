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
    private EvaluacionRepository evaluacionRepository;

    public List<Evaluacion> getAllEvaluaciones() {
        return evaluacionRepository.findAll();
    }

    public Optional<Evaluacion> getEvaluacionById(int id) {
        return evaluacionRepository.findById(id);
    }

    public Evaluacion saveEvaluacion(Evaluacion evaluacion) {
        return evaluacionRepository.save(evaluacion);
    }

    public void deleteEvaluacion(int id) {
        evaluacionRepository.deleteById(id);
    }
}
