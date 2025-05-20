package com.example.EduTech.Controller;

import com.example.EduTech.Model.Usuario;
import com.example.EduTech.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public String getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public String getUsuarioById(@PathVariable int id) {
        return usuarioService.getUsuario(id);
    }

    @PostMapping
    public String postUsuario(@RequestBody Usuario usuario) {
        return usuarioService.addUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    public String deleteUsuario(@PathVariable int id) {
        return usuarioService.deleteUsuario(id);
    }

    @PutMapping
    public String putUsuario(@RequestBody Usuario usuario) {
        return usuarioService.updateUsuario(usuario);
    }
}


