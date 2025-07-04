package com.example.EduTech.Repository;

import com.example.EduTech.Model.Usuarios.GerenteCurso;
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
public class GerenteCursoRepositoryTest {

    @Autowired
    private GerenteCursoRepository gerenteCursoRepository;

    @Autowired
    private TestEntityManager entityManager;

    private GerenteCurso gerentePrueba;

    @BeforeEach
    void setUp() {
        // Configuración inicial para cada test
        Usuario usuarioBase = new Usuario();
        usuarioBase.setNombreUsuario("gerente_user");
        usuarioBase.setCorreo("gerente@example.com");
        usuarioBase.setContrasena("password123");
        usuarioBase.setRol("GERENTE_CURSO");
        entityManager.persist(usuarioBase);

        gerentePrueba = new GerenteCurso();
        gerentePrueba.setNombreUsuario(usuarioBase.getNombreUsuario());
        gerentePrueba.setCorreo(usuarioBase.getCorreo());
        gerentePrueba.setContrasena(usuarioBase.getContrasena());
        gerentePrueba.setRol(usuarioBase.getRol());
        gerentePrueba.setActivo(true);

        entityManager.persistAndFlush(gerentePrueba);
    }

    @Test
    void whenFindById_thenReturnGerenteCurso() {
        // When
        Optional<GerenteCurso> foundGerente = gerenteCursoRepository.findById(gerentePrueba.getId());

        // Then
        assertTrue(foundGerente.isPresent());
        assertEquals(gerentePrueba.getCorreo(), foundGerente.get().getCorreo());
        assertTrue(foundGerente.get().isActivo());
    }

    @Test
    void whenFindByNonExistingId_thenReturnEmpty() {
        // When
        Optional<GerenteCurso> foundGerente = gerenteCursoRepository.findById(-1);

        // Then
        assertFalse(foundGerente.isPresent());
    }

    @Test
    void whenSaveGerenteCurso_thenGerenteIsPersisted() {
        // Given
        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setNombreUsuario("nuevo_gerente");
        usuarioNuevo.setCorreo("nuevo_gerente@example.com");
        usuarioNuevo.setContrasena("pass123");
        usuarioNuevo.setRol("GERENTE_CURSO");
        entityManager.persist(usuarioNuevo);

        GerenteCurso nuevoGerente = new GerenteCurso();
        nuevoGerente.setNombreUsuario(usuarioNuevo.getNombreUsuario());
        nuevoGerente.setCorreo(usuarioNuevo.getCorreo());
        nuevoGerente.setContrasena(usuarioNuevo.getContrasena());
        nuevoGerente.setRol(usuarioNuevo.getRol());
        nuevoGerente.setActivo(true);

        // When
        GerenteCurso savedGerente = gerenteCursoRepository.save(nuevoGerente);

        // Then
        assertNotNull(savedGerente.getId());
        assertEquals(nuevoGerente.getCorreo(), savedGerente.getCorreo());

        // Verify in DB
        GerenteCurso dbGerente = entityManager.find(GerenteCurso.class, savedGerente.getId());
        assertEquals(nuevoGerente.getCorreo(), dbGerente.getCorreo());
        assertTrue(dbGerente.isActivo());
    }

    @Test
    void whenUpdateGerenteCurso_thenGerenteIsUpdated() {
        // Given
        gerentePrueba.setActivo(false);

        // When
        GerenteCurso updatedGerente = gerenteCursoRepository.save(gerentePrueba);

        // Then
        assertEquals(gerentePrueba.getId(), updatedGerente.getId());
        assertFalse(updatedGerente.isActivo());

        // Verify in DB
        GerenteCurso dbGerente = entityManager.find(GerenteCurso.class, gerentePrueba.getId());
        assertFalse(dbGerente.isActivo());
    }

    @Test
    void whenDeleteGerenteCurso_thenGerenteIsRemoved() {
        // When
        gerenteCursoRepository.delete(gerentePrueba);

        // Then
        GerenteCurso deletedGerente = entityManager.find(GerenteCurso.class, gerentePrueba.getId());
        assertNull(deletedGerente);
    }

    @Test
    void whenFindAll_thenReturnAllGerentes() {
        // Given
        Usuario usuario2 = new Usuario();
        usuario2.setNombreUsuario("gerente2");
        usuario2.setCorreo("gerente2@example.com");
        usuario2.setContrasena("pass123");
        usuario2.setRol("GERENTE_CURSO");
        entityManager.persist(usuario2);

        GerenteCurso gerente2 = new GerenteCurso();
        gerente2.setNombreUsuario(usuario2.getNombreUsuario());
        gerente2.setCorreo(usuario2.getCorreo());
        gerente2.setContrasena(usuario2.getContrasena());
        gerente2.setRol(usuario2.getRol());
        gerente2.setActivo(true);
        entityManager.persistAndFlush(gerente2);

        // When
        List<GerenteCurso> allGerentes = gerenteCursoRepository.findAll();

        // Then
        assertEquals(2, allGerentes.size());
    }

    @Test
    void whenFindByActivoTrue_thenReturnActiveGerentes() {
        // Given
        Usuario usuarioInactivo = new Usuario();
        usuarioInactivo.setNombreUsuario("gerente_inactivo");
        usuarioInactivo.setCorreo("inactivo@example.com");
        usuarioInactivo.setContrasena("pass123");
        usuarioInactivo.setRol("GERENTE_CURSO");
        entityManager.persist(usuarioInactivo);

        GerenteCurso gerenteInactivo = new GerenteCurso();
        gerenteInactivo.setNombreUsuario(usuarioInactivo.getNombreUsuario());
        gerenteInactivo.setCorreo(usuarioInactivo.getCorreo());
        gerenteInactivo.setContrasena(usuarioInactivo.getContrasena());
        gerenteInactivo.setRol(usuarioInactivo.getRol());
        gerenteInactivo.setActivo(false);
        entityManager.persistAndFlush(gerenteInactivo);

        // When
        List<GerenteCurso> gerentesActivos = gerenteCursoRepository.findByActivoTrue();

        // Then
        assertEquals(1, gerentesActivos.size());
        assertEquals("gerente@example.com", gerentesActivos.get(0).getCorreo());
        assertTrue(gerentesActivos.get(0).isActivo());
    }
}