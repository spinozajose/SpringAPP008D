package com.example.EduTech.Repository;

import com.example.EduTech.Model.Usuarios.Estudiante;
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
public class EstudianteRepositoryTest {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Estudiante estudiantePrueba;

    @BeforeEach
    void setUp() {
        // Configuración inicial para cada test
        Usuario usuarioBase = new Usuario();
        usuarioBase.setNombreUsuario("estudiante_user");
        usuarioBase.setCorreo("estudiante@example.com");
        usuarioBase.setContrasena("password123");
        usuarioBase.setRol("ESTUDIANTE");
        entityManager.persist(usuarioBase);

        estudiantePrueba = new Estudiante();
        estudiantePrueba.setNombreUsuario(usuarioBase.getNombreUsuario());
        estudiantePrueba.setCorreo(usuarioBase.getCorreo());
        estudiantePrueba.setContrasena(usuarioBase.getContrasena());
        estudiantePrueba.setRol(usuarioBase.getRol());
        estudiantePrueba.setActivo(true);
        estudiantePrueba.setNivelAcademico("Secundaria");

        entityManager.persistAndFlush(estudiantePrueba);
    }

    @Test
    void whenFindById_thenReturnEstudiante() {
        // When
        Optional<Estudiante> foundEstudiante = estudianteRepository.findById(estudiantePrueba.getId());

        // Then
        assertTrue(foundEstudiante.isPresent());
        assertEquals("estudiante@example.com", foundEstudiante.get().getCorreo());
        assertTrue(foundEstudiante.get().isActivo());
        assertEquals("Secundaria", foundEstudiante.get().getNivelAcademico());
    }

    @Test
    void whenFindByNonExistingId_thenReturnEmpty() {
        // When
        Optional<Estudiante> foundEstudiante = estudianteRepository.findById(-1);

        // Then
        assertFalse(foundEstudiante.isPresent());
    }

    @Test
    void whenSaveEstudiante_thenEstudianteIsPersisted() {
        // Given
        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setNombreUsuario("nuevo_estudiante");
        usuarioNuevo.setCorreo("nuevo_estudiante@example.com");
        usuarioNuevo.setContrasena("pass123");
        usuarioNuevo.setRol("ESTUDIANTE");
        entityManager.persist(usuarioNuevo);

        Estudiante nuevoEstudiante = new Estudiante();
        nuevoEstudiante.setNombreUsuario(usuarioNuevo.getNombreUsuario());
        nuevoEstudiante.setCorreo(usuarioNuevo.getCorreo());
        nuevoEstudiante.setContrasena(usuarioNuevo.getContrasena());
        nuevoEstudiante.setRol(usuarioNuevo.getRol());
        nuevoEstudiante.setActivo(true);
        nuevoEstudiante.setNivelAcademico("Universidad");

        // When
        Estudiante savedEstudiante = estudianteRepository.save(nuevoEstudiante);

        // Then
        assertNotNull(savedEstudiante.getId());
        assertEquals("nuevo_estudiante@example.com", savedEstudiante.getCorreo());

        // Verify in DB
        Estudiante dbEstudiante = entityManager.find(Estudiante.class, savedEstudiante.getId());
        assertEquals("Universidad", dbEstudiante.getNivelAcademico());
        assertTrue(dbEstudiante.isActivo());
    }

    @Test
    void whenUpdateEstudiante_thenEstudianteIsUpdated() {
        // Given
        estudiantePrueba.setNivelAcademico("Bachillerato");
        estudiantePrueba.setActivo(false);

        // When
        Estudiante updatedEstudiante = estudianteRepository.save(estudiantePrueba);

        // Then
        assertEquals(estudiantePrueba.getId(), updatedEstudiante.getId());
        assertEquals("Bachillerato", updatedEstudiante.getNivelAcademico());
        assertFalse(updatedEstudiante.isActivo());

        // Verify in DB
        Estudiante dbEstudiante = entityManager.find(Estudiante.class, estudiantePrueba.getId());
        assertEquals("Bachillerato", dbEstudiante.getNivelAcademico());
    }

    @Test
    void whenDeleteEstudiante_thenEstudianteIsRemoved() {
        // When
        estudianteRepository.delete(estudiantePrueba);

        // Then
        Estudiante deletedEstudiante = entityManager.find(Estudiante.class, estudiantePrueba.getId());
        assertNull(deletedEstudiante);
    }

    @Test
    void whenFindAll_thenReturnAllEstudiantes() {
        // Given
        Usuario usuario2 = new Usuario();
        usuario2.setNombreUsuario("estudiante2");
        usuario2.setCorreo("estudiante2@example.com");
        usuario2.setContrasena("pass123");
        usuario2.setRol("ESTUDIANTE");
        entityManager.persist(usuario2);

        Estudiante estudiante2 = new Estudiante();
        estudiante2.setNombreUsuario(usuario2.getNombreUsuario());
        estudiante2.setCorreo(usuario2.getCorreo());
        estudiante2.setContrasena(usuario2.getContrasena());
        estudiante2.setRol(usuario2.getRol());
        estudiante2.setActivo(true);
        estudiante2.setNivelAcademico("Primaria");
        entityManager.persistAndFlush(estudiante2);

        // When
        List<Estudiante> allEstudiantes = estudianteRepository.findAll();

        // Then
        assertEquals(2, allEstudiantes.size());
    }

    @Test
    void whenFindByActivoTrue_thenReturnActiveEstudiantes() {
        // Given
        Usuario usuarioInactivo = new Usuario();
        usuarioInactivo.setNombreUsuario("estudiante_inactivo");
        usuarioInactivo.setCorreo("inactivo@example.com");
        usuarioInactivo.setContrasena("pass123");
        usuarioInactivo.setRol("ESTUDIANTE");
        entityManager.persist(usuarioInactivo);

        Estudiante estudianteInactivo = new Estudiante();
        estudianteInactivo.setNombreUsuario(usuarioInactivo.getNombreUsuario());
        estudianteInactivo.setCorreo(usuarioInactivo.getCorreo());
        estudianteInactivo.setContrasena(usuarioInactivo.getContrasena());
        estudianteInactivo.setRol(usuarioInactivo.getRol());
        estudianteInactivo.setActivo(false);
        estudianteInactivo.setNivelAcademico("Secundaria");
        entityManager.persistAndFlush(estudianteInactivo);

        // When
        List<Estudiante> estudiantesActivos = estudianteRepository.findByActivoTrue();

        // Then
        assertEquals(1, estudiantesActivos.size());
        assertEquals("estudiante@example.com", estudiantesActivos.get(0).getCorreo());
        assertTrue(estudiantesActivos.get(0).isActivo());
    }

    @Test
    void whenFindByNivelAcademico_thenReturnEstudiantesForNivel() {
        // Given
        Usuario usuario2 = new Usuario();
        usuario2.setNombreUsuario("estudiante2");
        usuario2.setCorreo("estudiante2@example.com");
        usuario2.setContrasena("pass123");
        usuario2.setRol("ESTUDIANTE");
        entityManager.persist(usuario2);

        Estudiante estudiante2 = new Estudiante();
        estudiante2.setNombreUsuario(usuario2.getNombreUsuario());
        estudiante2.setCorreo(usuario2.getCorreo());
        estudiante2.setContrasena(usuario2.getContrasena());
        estudiante2.setRol(usuario2.getRol());
        estudiante2.setActivo(true);
        estudiante2.setNivelAcademico("Secundaria");
        entityManager.persistAndFlush(estudiante2);

        Usuario usuario3 = new Usuario();
        usuario3.setNombreUsuario("estudiante3");
        usuario3.setCorreo("estudiante3@example.com");
        usuario3.setContrasena("pass123");
        usuario3.setRol("ESTUDIANTE");
        entityManager.persist(usuario3);

        Estudiante estudiante3 = new Estudiante();
        estudiante3.setNombreUsuario(usuario3.getNombreUsuario());
        estudiante3.setCorreo(usuario3.getCorreo());
        estudiante3.setContrasena(usuario3.getContrasena());
        estudiante3.setRol(usuario3.getRol());
        estudiante3.setActivo(true);
        estudiante3.setNivelAcademico("Universidad");
        entityManager.persistAndFlush(estudiante3);

        // When
        List<Estudiante> secundariaEstudiantes = estudianteRepository.findByNivelAcademico("Secundaria");
        List<Estudiante> universidadEstudiantes = estudianteRepository.findByNivelAcademico("Universidad");

        // Then
        assertEquals(2, secundariaEstudiantes.size());
        assertEquals(1, universidadEstudiantes.size());
        assertEquals("Universidad", universidadEstudiantes.get(0).getNivelAcademico());
    }
}