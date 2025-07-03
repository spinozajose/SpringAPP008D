package com.example.EduTech.Repository;


import com.example.EduTech.Model.Usuarios.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Usuario usuarioPrueba;

    @BeforeEach
    void setUp() {
        // Configuración inicial para cada test
        usuarioPrueba = new Usuario();
        usuarioPrueba.setNombreUsuario("ana_garcia");
        usuarioPrueba.setCorreo("ana@example.com");
        usuarioPrueba.setContrasena("password123");
        usuarioPrueba.setRol("ESTUDIANTE");

        entityManager.persistAndFlush(usuarioPrueba);
    }


    @Test
    void whenFindById_thenReturnUsuario() {
        // When
        Optional<Usuario> foundUsuario = usuarioRepository.findById(usuarioPrueba.getId());

        // Then
        assertTrue(foundUsuario.isPresent());
        assertEquals(usuarioPrueba.getNombreUsuario(), foundUsuario.get().getNombreUsuario());
        assertEquals(usuarioPrueba.getRol(), foundUsuario.get().getRol());
    }


    @Test
    void whenFindByNonExistingNombreUsuario_thenReturnEmpty() {
        // When
        Optional<Usuario> foundUsuario = usuarioRepository.findByNombreUsuario("no_existe");

        // Then
        assertFalse(foundUsuario.isPresent());
    }

    @Test
    void whenSaveUsuario_thenUsuarioIsPersisted() {
        // Given
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario("nuevo_usuario");
        nuevoUsuario.setCorreo("nuevo@example.com");
        nuevoUsuario.setContrasena("nuevaPass123");
        nuevoUsuario.setRol("PROFESOR");

        // When
        Usuario savedUsuario = usuarioRepository.save(nuevoUsuario);

        // Then
        assertNotNull(savedUsuario.getId());
        assertEquals(nuevoUsuario.getNombreUsuario(), savedUsuario.getNombreUsuario());

        // Verify in DB
        Usuario dbUsuario = entityManager.find(Usuario.class, savedUsuario.getId());
        assertEquals(nuevoUsuario.getCorreo(), dbUsuario.getCorreo());
        assertEquals(nuevoUsuario.getRol(), dbUsuario.getRol());
    }

    @Test
    void whenUpdateUsuario_thenUsuarioIsUpdated() {
        // Given
        usuarioPrueba.setCorreo("nuevo_email@example.com");
        usuarioPrueba.setRol("ADMINISTRADOR");

        // When
        Usuario updatedUsuario = usuarioRepository.save(usuarioPrueba);

        // Then
        assertEquals(usuarioPrueba.getId(), updatedUsuario.getId());
        assertEquals("nuevo_email@example.com", updatedUsuario.getCorreo());
        assertEquals("ADMINISTRADOR", updatedUsuario.getRol());

        // Verify in DB
        Usuario dbUsuario = entityManager.find(Usuario.class, usuarioPrueba.getId());
        assertEquals("nuevo_email@example.com", dbUsuario.getCorreo());
    }

    @Test
    void whenDeleteUsuario_thenUsuarioIsRemoved() {
        // When
        usuarioRepository.delete(usuarioPrueba);

        // Then
        Usuario deletedUsuario = entityManager.find(Usuario.class, usuarioPrueba.getId());
        assertNull(deletedUsuario);
    }

    @Test
    void existsByNombreUsuario_whenExists_thenReturnTrue() {
        // When
        boolean exists = usuarioRepository.existsByNombreUsuario("ana_garcia");

        // Then
        assertTrue(exists);
    }

    @Test
    void existsByNombreUsuario_whenNotExists_thenReturnFalse() {
        // When
        boolean exists = usuarioRepository.existsByNombreUsuario("no_existe");

        // Then
        assertFalse(exists);
    }

    @Test
    void existsByCorreo_whenExists_thenReturnTrue() {
        // When
        boolean exists = usuarioRepository.existsByCorreo("ana@example.com");

        // Then
        assertTrue(exists);
    }

    @Test
    void existsByCorreo_whenNotExists_thenReturnFalse() {
        // When
        boolean exists = usuarioRepository.existsByCorreo("no_existe@example.com");

        // Then
        assertFalse(exists);
    }

    @Test
    void whenFindByRol_thenReturnUsuariosWithThatRol() {
        // Given
        Usuario adminUsuario = new Usuario();
        adminUsuario.setNombreUsuario("admin");
        adminUsuario.setCorreo("admin@example.com");
        adminUsuario.setContrasena("adminPass");
        adminUsuario.setRol("ADMINISTRADOR");
        entityManager.persistAndFlush(adminUsuario);

        // When
        List<Usuario> estudiantes = usuarioRepository.findByRol("ESTUDIANTE");
        List<Usuario> administradores = usuarioRepository.findByRol("ADMINISTRADOR");

        // Then
        assertEquals(1, estudiantes.size());
        assertEquals("ana_garcia", estudiantes.get(0).getNombreUsuario());

        assertEquals(1, administradores.size());
        assertEquals("admin", administradores.get(0).getNombreUsuario());
    }
}