package com.example.EduTech.Model.Usuarios;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class GerenteCurso extends Usuario {
    private boolean activo;
}
