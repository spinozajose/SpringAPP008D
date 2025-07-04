package com.example.EduTech.Repository;

import com.example.EduTech.Model.Usuarios.GerenteCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GerenteCursoRepository extends JpaRepository<GerenteCurso, Integer> {
    List<GerenteCurso> findByActivoTrue();
}
