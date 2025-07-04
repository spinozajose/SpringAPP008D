package com.example.EduTech.Repository;

import com.example.EduTech.Model.Usuarios.Docente;
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
public class DocenteRepositoryTest {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Docente docentePrueba;

    @BeforeEach
    void setUp() {
        // Configuración inicial para cada test
        Usuario usuarioBase = new Usuario();
        usuarioBase.setNombreUsuario("docente_user");
        usuarioBase.setCorreo("docente@example.com");
        usuarioBase.setContrasena("password123");
        usuarioBase.setRol("DOCENTE");
        entityManager.persist(usuarioBase);

        docentePrueba = new Docente();
        docentePrueba.setNombreUsuario(usuarioBase.getNombreUsuario());
        docentePrueba.setCorreo(usuarioBase.getCorreo());
        docentePrueba.setContrasena(usuarioBase.getContrasena());
        docentePrueba.setRol(usuarioBase.getRol());
        docentePrueba.setActivo(true);
        docentePrueba.setEspecialidad("Matemáticas");

        entityManager.persistAndFlush(docentePrueba);
    }

    @Test
    void whenFindById_thenReturnDocente() {
        // When
        Optional<Docente> foundDocente = docenteRepository.findById(docentePrueba.getId());

        // Then
        assertTrue(foundDocente.isPresent());
        assertEquals("docente@example.com", foundDocente.get().getCorreo());
        assertTrue(foundDocente.get().isActivo());
        assertEquals("Matemáticas", foundDocente.get().getEspecialidad());
    }

    @Test
    void whenFindByNonExistingId_thenReturnEmpty() {
        // When
        Optional<Docente> foundDocente = docenteRepository.findById(-1);

        // Then
        assertFalse(foundDocente.isPresent());
    }

    @Test
    void whenSaveDocente_thenDocenteIsPersisted() {
        // Given
        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setNombreUsuario("nuevo_docente");
        usuarioNuevo.setCorreo("nuevo_docente@example.com");
        usuarioNuevo.setContrasena("pass123");
        usuarioNuevo.setRol("DOCENTE");
        entityManager.persist(usuarioNuevo);

        Docente nuevoDocente = new Docente();
        nuevoDocente.setNombreUsuario(usuarioNuevo.getNombreUsuario());
        nuevoDocente.setCorreo(usuarioNuevo.getCorreo());
        nuevoDocente.setContrasena(usuarioNuevo.getContrasena());
        nuevoDocente.setRol(usuarioNuevo.getRol());
        nuevoDocente.setActivo(true);
        nuevoDocente.setEspecialidad("Física");

        // When
        Docente savedDocente = docenteRepository.save(nuevoDocente);

        // Then
        assertNotNull(savedDocente.getId());
        assertEquals("nuevo_docente@example.com", savedDocente.getCorreo());

        // Verify in DB
        Docente dbDocente = entityManager.find(Docente.class, savedDocente.getId());
        assertEquals("Física", dbDocente.getEspecialidad());
        assertTrue(dbDocente.isActivo());
    }

    @Test
    void whenUpdateDocente_thenDocenteIsUpdated() {
        // Given
        docentePrueba.setEspecialidad("Matemáticas Avanzadas");
        docentePrueba.setActivo(false);

        // When
        Docente updatedDocente = docenteRepository.save(docentePrueba);

        // Then
        assertEquals(docentePrueba.getId(), updatedDocente.getId());
        assertEquals("Matemáticas Avanzadas", updatedDocente.getEspecialidad());
        assertFalse(updatedDocente.isActivo());

        // Verify in DB
        Docente dbDocente = entityManager.find(Docente.class, docentePrueba.getId());
        assertEquals("Matemáticas Avanzadas", dbDocente.getEspecialidad());
    }

    @Test
    void whenDeleteDocente_thenDocenteIsRemoved() {
        // When
        docenteRepository.delete(docentePrueba);

        // Then
        Docente deletedDocente = entityManager.find(Docente.class, docentePrueba.getId());
        assertNull(deletedDocente);
    }

    @Test
    void whenFindAll_thenReturnAllDocentes() {
        // Given
        Usuario usuario2 = new Usuario();
        usuario2.setNombreUsuario("docente2");
        usuario2.setCorreo("docente2@example.com");
        usuario2.setContrasena("pass123");
        usuario2.setRol("DOCENTE");
        entityManager.persist(usuario2);

        Docente docente2 = new Docente();
        docente2.setNombreUsuario(usuario2.getNombreUsuario());
        docente2.setCorreo(usuario2.getCorreo());
        docente2.setContrasena(usuario2.getContrasena());
        docente2.setRol(usuario2.getRol());
        docente2.setActivo(true);
        docente2.setEspecialidad("Literatura");
        entityManager.persistAndFlush(docente2);

        // When
        List<Docente> allDocentes = docenteRepository.findAll();

        // Then
        assertEquals(2, allDocentes.size());
    }

    @Test
    void whenFindByActivoTrue_thenReturnActiveDocentes() {
        // Given
        Usuario usuarioInactivo = new Usuario();
        usuarioInactivo.setNombreUsuario("docente_inactivo");
        usuarioInactivo.setCorreo("inactivo@example.com");
        usuarioInactivo.setContrasena("pass123");
        usuarioInactivo.setRol("DOCENTE");
        entityManager.persist(usuarioInactivo);

        Docente docenteInactivo = new Docente();
        docenteInactivo.setNombreUsuario(usuarioInactivo.getNombreUsuario());
        docenteInactivo.setCorreo(usuarioInactivo.getCorreo());
        docenteInactivo.setContrasena(usuarioInactivo.getContrasena());
        docenteInactivo.setRol(usuarioInactivo.getRol());
        docenteInactivo.setActivo(false);
        docenteInactivo.setEspecialidad("Historia");
        entityManager.persistAndFlush(docenteInactivo);

        // When
        List<Docente> docentesActivos = docenteRepository.findByActivoTrue();

        // Then
        assertEquals(1, docentesActivos.size());
        assertEquals("docente@example.com", docentesActivos.get(0).getCorreo());
        assertTrue(docentesActivos.get(0).isActivo());
    }

    @Test
    void whenFindByEspecialidad_thenReturnDocentesForEspecialidad() {
        // Given
        Usuario usuario2 = new Usuario();
        usuario2.setNombreUsuario("docente2");
        usuario2.setCorreo("docente2@example.com");
        usuario2.setContrasena("pass123");
        usuario2.setRol("DOCENTE");
        entityManager.persist(usuario2);

        Docente docente2 = new Docente();
        docente2.setNombreUsuario(usuario2.getNombreUsuario());
        docente2.setCorreo(usuario2.getCorreo());
        docente2.setContrasena(usuario2.getContrasena());
        docente2.setRol(usuario2.getRol());
        docente2.setActivo(true);
        docente2.setEspecialidad("Matemáticas");
        entityManager.persistAndFlush(docente2);

        Usuario usuario3 = new Usuario();
        usuario3.setNombreUsuario("docente3");
        usuario3.setCorreo("docente3@example.com");
        usuario3.setContrasena("pass123");
        usuario3.setRol("DOCENTE");
        entityManager.persist(usuario3);

        Docente docente3 = new Docente();
        docente3.setNombreUsuario(usuario3.getNombreUsuario());
        docente3.setCorreo(usuario3.getCorreo());
        docente3.setContrasena(usuario3.getContrasena());
        docente3.setRol(usuario3.getRol());
        docente3.setActivo(true);
        docente3.setEspecialidad("Física");
        entityManager.persistAndFlush(docente3);

        // When
        List<Docente> matematicasDocentes = docenteRepository.findByEspecialidad("Matemáticas");
        List<Docente> fisicaDocentes = docenteRepository.findByEspecialidad("Física");

        // Then
        assertEquals(2, matematicasDocentes.size());
        assertEquals(1, fisicaDocentes.size());
        assertEquals("Física", fisicaDocentes.get(0).getEspecialidad());
    }

    @Test
    void whenFindByEspecialidadContainingIgnoreCase_thenReturnMatchingDocentes() {
        // Given
        Usuario usuario2 = new Usuario();
        usuario2.setNombreUsuario("docente2");
        usuario2.setCorreo("docente2@example.com");
        usuario2.setContrasena("pass123");
        usuario2.setRol("DOCENTE");
        entityManager.persist(usuario2);

        Docente docente2 = new Docente();
        docente2.setNombreUsuario(usuario2.getNombreUsuario());
        docente2.setCorreo(usuario2.getCorreo());
        docente2.setContrasena(usuario2.getContrasena());
        docente2.setRol(usuario2.getRol());
        docente2.setActivo(true);
        docente2.setEspecialidad("Matemáticas Avanzadas");
        entityManager.persistAndFlush(docente2);

        // When
        List<Docente> matematicasDocentes = docenteRepository.findByEspecialidadContainingIgnoreCase("matem");
        List<Docente> noResults = docenteRepository.findByEspecialidadContainingIgnoreCase("quimica");

        // Then
        assertEquals(2, matematicasDocentes.size());
        assertEquals(0, noResults.size());
    }
}