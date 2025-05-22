package com.example.EduTech.Model.Usuarios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Estudiante extends Usuario {

    private boolean activo;
    private String nivelAcademico;
}