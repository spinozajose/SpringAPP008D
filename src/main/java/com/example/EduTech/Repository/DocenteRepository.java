package com.example.EduTech.Repository;

import com.example.EduTech.Model.Usuarios.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Integer> {
    List<Docente> findByActivoTrue();
    List<Docente> findByEspecialidad(String especialidad);
    List<Docente> findByEspecialidadContainingIgnoreCase(String especialidad);
}