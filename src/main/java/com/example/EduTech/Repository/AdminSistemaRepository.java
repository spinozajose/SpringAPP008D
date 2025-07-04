package com.example.EduTech.Repository;

import com.example.EduTech.Model.Usuarios.AdminSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminSistemaRepository extends JpaRepository<AdminSistema, Integer> {
    List<AdminSistema> findByActivoTrue();
    Optional<AdminSistema> findByCorreo(String correo);
}