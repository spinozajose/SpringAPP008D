package com.example.EduTech.EntityTest;

import com.example.EduTech.Model.Usuarios.Usuario;
import com.example.EduTech.Repository.UsuarioRepository;
import com.example.EduTech.Service.UsuarioService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @Test
    void findAllUsuariosTest() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        assertNotNull(usuarios);
    }

    @Test
    void checkNombreUsuarioTest() {
        Usuario usuario = usuarioRepository.findById(1).orElse(null);
        assertNotNull(usuario);
        assertEquals("ana_garcia", usuario.getNombreUsuario());
    }

    @Test
    void getAllUsuariosControllerTest() {
        Mockito.when(usuarioService.obtenerUsuarios()).thenReturn(List.of(new Usuario()));
        try {
            mockMvc.perform(get("/usuarios"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }
    }
}