package com.example.EduTech.Repository;

import com.example.EduTech.Model.Usuarios.Usuario;
import com.example.EduTech.Repository.UsuarioRepository;
import com.example.EduTech.Service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest
@ActiveProfiles("test") // Asegúrate de que use el perfil "test"
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // ¡IMPORTANTE! Evita que Spring reemplace MySQL con H2
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        // Inserta datos de prueba en MySQL antes de cada test
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("ana_garcia");
        usuarioRepository.save(usuario);
    }

    @Test
    void findAllUsuariosTest() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        assertFalse(usuarios.isEmpty());
    }

    @Test
    void checkNombreUsuarioTest() {
        Usuario usuario = usuarioRepository.findById(1).orElseThrow();
        assertEquals("ana_garcia", usuario.getNombreUsuario());
    }
}
