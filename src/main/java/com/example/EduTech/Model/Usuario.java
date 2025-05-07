package com.example.EduTech.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {
    private int id;
    private String NombreUsuario;
    private String Contrasena;
    private String Correo;
}
