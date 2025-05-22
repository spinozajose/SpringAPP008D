package com.example.EduTech.Service;

import com.example.EduTech.Model.Usuarios.Usuario;
import com.example.EduTech.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String addUsuario(Usuario usuario) {
        return usuarioRepository.addUsuario(usuario);
    }

    public String updateUsuario(Usuario usuario) {
        return usuarioRepository.updateUsuario(usuario);
    }

    public String deleteUsuario(int id) {
        return usuarioRepository.removeUsuario(id);
    }

    public String getUsuario(int id) {
        return usuarioRepository.getUsuario(id);
    }

    public String getAllUsuarios() {
        return usuarioRepository.getUsuarios();
    }
}
