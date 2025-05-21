package com.example.EduTech.Repository;

import com.example.EduTech.Model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository {
    private List<Usuario> usuarios = new ArrayList<>();

    public UsuarioRepository() {

    }

    public String getUsuario(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario.toString();
            }
        }
        return "Usuario no encontrado";
    }

    public String addUsuario(Usuario usuario) {
        usuarios.add(usuario);
        return "Usuario agregado";
    }

    public String removeUsuario(int id){
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                usuarios.remove(usuario);
                return "Usuario eliminado";
            }
        }
        return "Usuario no encontrado";
    }

    public String updateUsuario(Usuario nuevoUsuario) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equalsIgnoreCase(nuevoUsuario.getNombreUsuario())) {
                usuarios.remove(usuario);
                usuarios.add(nuevoUsuario);
                return "Usuario actualizado";
            }
        }
        return "Usuario no encontrado";
    }

    public String getUsuarios() {
        String output = "";
        for (Usuario usuario : usuarios) {
            output += " id: " + usuario.getId() + "\n";
            output += " nombreUsuario: " + usuario.getNombreUsuario() + "\n";
            output += " contrasena: " + usuario.getContrasena() + "\n";
            output += " correo: " + usuario.getCorreo() + "\n";
        }
        if (output.isEmpty()){
            return "No hay usuarios registrados";
        }else{
            return output;
        }
    }
}

