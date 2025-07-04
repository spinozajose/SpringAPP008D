package com.example.EduTech.Repository;

import com.example.EduTech.Model.Usuarios.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
    List<Estudiante> findByActivoTrue();
    List<Estudiante> findByNivelAcademico(String nivelAcademico);
}