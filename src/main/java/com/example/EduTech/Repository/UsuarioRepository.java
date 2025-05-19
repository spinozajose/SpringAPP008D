package com.example.EduTech.Repository;

import com.example.EduTech.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // JpaRepository ya trae métodos CRUD por defecto
}
