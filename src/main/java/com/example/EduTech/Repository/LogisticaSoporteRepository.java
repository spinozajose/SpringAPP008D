package com.example.EduTech.Repository;

import com.example.EduTech.Model.Usuarios.LogisticaSoporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogisticaSoporteRepository extends JpaRepository<LogisticaSoporte, Integer> {
    boolean existsByCorreo(String correo);
    List<LogisticaSoporte> findByActivoTrue();
    List<LogisticaSoporte> findByAreaResponsable(String areaResponsable);
}