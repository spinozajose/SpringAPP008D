package com.example.EduTech.Service;

import com.example.EduTech.Model.Usuario;
import com.example.EduTech.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerUsuarioPorId(int id) {
        return usuarioRepository.findById(id);
    }

    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(int id, Usuario usuarioActualizado) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
            usuario.setContrasena(usuarioActualizado.getContrasena());
            usuario.setCorreo(usuarioActualizado.getCorreo());
            return usuarioRepository.save(usuario);
        } else {
            return null;
        }
    }

    public boolean eliminarUsuario(int id) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            usuarioRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
