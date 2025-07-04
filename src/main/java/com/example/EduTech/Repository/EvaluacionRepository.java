package com.example.EduTech.Repository;

import com.example.EduTech.Model.Curso.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer> {
    List<Evaluacion> findByMateria(String materia);
    List<Evaluacion> findByNotaGreaterThan(double nota);
}