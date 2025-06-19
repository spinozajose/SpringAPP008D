package com.example.SpringApp008D.Service;

import com.example.SpringApp008D.Model.Usuario;
import com.example.SpringApp008D.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuariosRepository;

    public List<Usuario> obtenerUsuarios() {
        return usuariosRepository.findAll();
    }

    public void agregarUsuario(Usuario usuario) {
        usuariosRepository.save(usuario);
    }

    public Optional<Usuario> obtenerUsuario(int id) {
        return usuariosRepository.findById(id);
    }

    public void eliminarUsuario(int id) {
        usuariosRepository.deleteById(id);
    }

    public void actualizarUsuario(int id, Usuario usuario) {
        Usuario us = usuariosRepository.findById(id).get();
        us.setNombreUsuario(usuario.getNombreUsuario());
        us.setContraseña(usuario.getContraseña());
        us.setCorreo(usuario.getCorreo());
    }
}
