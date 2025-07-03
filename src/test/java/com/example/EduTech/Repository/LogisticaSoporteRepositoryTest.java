package com.example.EduTech.Repository;

import com.example.EduTech.Model.Usuarios.LogisticaSoporte;
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
public class LogisticaSoporteRepositoryTest {

    @Autowired
    private LogisticaSoporteRepository logisticaSoporteRepository;

    @Autowired
    private TestEntityManager entityManager;

    private LogisticaSoporte logisticaPrueba;

    @BeforeEach
    void setUp() {
        // Configuración inicial para cada test
        Usuario usuarioBase = new Usuario();
        usuarioBase.setNombreUsuario("logistica_user");
        usuarioBase.setCorreo("logistica@example.com");
        usuarioBase.setContrasena("password123");
        usuarioBase.setRol("LOGISTICA_SOPORTE");
        entityManager.persist(usuarioBase);

        logisticaPrueba = new LogisticaSoporte();
        logisticaPrueba.setNombreUsuario(usuarioBase.getNombreUsuario());
        logisticaPrueba.setCorreo(usuarioBase.getCorreo());
        logisticaPrueba.setContrasena(usuarioBase.getContrasena());
        logisticaPrueba.setRol(usuarioBase.getRol());
        logisticaPrueba.setAreaResponsable("Soporte Técnico");
        logisticaPrueba.setActivo(true);

        entityManager.persistAndFlush(logisticaPrueba);
    }

    @Test
    void whenFindById_thenReturnLogisticaSoporte() {
        // When
        Optional<LogisticaSoporte> foundLogistica = logisticaSoporteRepository.findById(logisticaPrueba.getId());

        // Then
        assertTrue(foundLogistica.isPresent());
        assertEquals(logisticaPrueba.getAreaResponsable(), foundLogistica.get().getAreaResponsable());
        assertTrue(foundLogistica.get().isActivo());
    }

    @Test
    void whenFindByNonExistingId_thenReturnEmpty() {
        // When
        Optional<LogisticaSoporte> foundLogistica = logisticaSoporteRepository.findById(-1);

        // Then
        assertFalse(foundLogistica.isPresent());
    }

    @Test
    void whenSaveLogisticaSoporte_thenLogisticaIsPersisted() {
        // Given
        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setNombreUsuario("nuevo_logistica");
        usuarioNuevo.setCorreo("nuevo@example.com");
        usuarioNuevo.setContrasena("pass123");
        usuarioNuevo.setRol("LOGISTICA_SOPORTE");
        entityManager.persist(usuarioNuevo);

        LogisticaSoporte nuevaLogistica = new LogisticaSoporte();
        nuevaLogistica.setNombreUsuario(usuarioNuevo.getNombreUsuario());
        nuevaLogistica.setCorreo(usuarioNuevo.getCorreo());
        nuevaLogistica.setContrasena(usuarioNuevo.getContrasena());
        nuevaLogistica.setRol(usuarioNuevo.getRol());
        nuevaLogistica.setAreaResponsable("Logística");
        nuevaLogistica.setActivo(true);

        // When
        LogisticaSoporte savedLogistica = logisticaSoporteRepository.save(nuevaLogistica);

        // Then
        assertNotNull(savedLogistica.getId());
        assertEquals(nuevaLogistica.getAreaResponsable(), savedLogistica.getAreaResponsable());

        // Verify in DB
        LogisticaSoporte dbLogistica = entityManager.find(LogisticaSoporte.class, savedLogistica.getId());
        assertEquals(nuevaLogistica.getCorreo(), dbLogistica.getCorreo());
        assertEquals(nuevaLogistica.getAreaResponsable(), dbLogistica.getAreaResponsable());
    }

    @Test
    void whenUpdateLogisticaSoporte_thenLogisticaIsUpdated() {
        // Given
        logisticaPrueba.setAreaResponsable("Soporte Avanzado");
        logisticaPrueba.setActivo(false);

        // When
        LogisticaSoporte updatedLogistica = logisticaSoporteRepository.save(logisticaPrueba);

        // Then
        assertEquals(logisticaPrueba.getId(), updatedLogistica.getId());
        assertEquals("Soporte Avanzado", updatedLogistica.getAreaResponsable());
        assertFalse(updatedLogistica.isActivo());

        // Verify in DB
        LogisticaSoporte dbLogistica = entityManager.find(LogisticaSoporte.class, logisticaPrueba.getId());
        assertEquals("Soporte Avanzado", dbLogistica.getAreaResponsable());
    }

    @Test
    void whenDeleteLogisticaSoporte_thenLogisticaIsRemoved() {
        // When
        logisticaSoporteRepository.delete(logisticaPrueba);

        // Then
        LogisticaSoporte deletedLogistica = entityManager.find(LogisticaSoporte.class, logisticaPrueba.getId());
        assertNull(deletedLogistica);
    }

    @Test
    void existsByCorreo_whenExists_thenReturnTrue() {
        // When
        boolean exists = logisticaSoporteRepository.existsByCorreo("logistica@example.com");

        // Then
        assertTrue(exists);
    }

    @Test
    void existsByCorreo_whenNotExists_thenReturnFalse() {
        // When
        boolean exists = logisticaSoporteRepository.existsByCorreo("no_existe@example.com");

        // Then
        assertFalse(exists);
    }

    @Test
    void whenFindByActivoTrue_thenReturnActiveLogisticas() {
        // Given
        Usuario usuarioInactivo = new Usuario();
        usuarioInactivo.setNombreUsuario("logistica_inactiva");
        usuarioInactivo.setCorreo("inactivo@example.com");
        usuarioInactivo.setContrasena("pass123");
        usuarioInactivo.setRol("LOGISTICA_SOPORTE");
        entityManager.persist(usuarioInactivo);

        LogisticaSoporte logisticaInactiva = new LogisticaSoporte();
        logisticaInactiva.setNombreUsuario(usuarioInactivo.getNombreUsuario());
        logisticaInactiva.setCorreo(usuarioInactivo.getCorreo());
        logisticaInactiva.setContrasena(usuarioInactivo.getContrasena());
        logisticaInactiva.setRol(usuarioInactivo.getRol());
        logisticaInactiva.setAreaResponsable("Soporte Técnico");
        logisticaInactiva.setActivo(false);
        entityManager.persistAndFlush(logisticaInactiva);

        // When
        List<LogisticaSoporte> logisticasActivas = logisticaSoporteRepository.findByActivoTrue();

        // Then
        assertEquals(1, logisticasActivas.size());
        assertEquals("logistica@example.com", logisticasActivas.get(0).getCorreo());
        assertTrue(logisticasActivas.get(0).isActivo());
    }

    @Test
    void whenFindByAreaResponsable_thenReturnLogisticasInThatArea() {
        // Given
        Usuario usuario2 = new Usuario();
        usuario2.setNombreUsuario("logistica2");
        usuario2.setCorreo("logistica2@example.com");
        usuario2.setContrasena("pass123");
        usuario2.setRol("LOGISTICA_SOPORTE");
        entityManager.persist(usuario2);

        LogisticaSoporte logistica2 = new LogisticaSoporte();
        logistica2.setNombreUsuario(usuario2.getNombreUsuario());
        logistica2.setCorreo(usuario2.getCorreo());
        logistica2.setContrasena(usuario2.getContrasena());
        logistica2.setRol(usuario2.getRol());
        logistica2.setAreaResponsable("Soporte Técnico");
        logistica2.setActivo(true);
        entityManager.persistAndFlush(logistica2);

        Usuario usuario3 = new Usuario();
        usuario3.setNombreUsuario("logistica3");
        usuario3.setCorreo("logistica3@example.com");
        usuario3.setContrasena("pass123");
        usuario3.setRol("LOGISTICA_SOPORTE");
        entityManager.persist(usuario3);

        LogisticaSoporte logistica3 = new LogisticaSoporte();
        logistica3.setNombreUsuario(usuario3.getNombreUsuario());
        logistica3.setCorreo(usuario3.getCorreo());
        logistica3.setContrasena(usuario3.getContrasena());
        logistica3.setRol(usuario3.getRol());
        logistica3.setAreaResponsable("Logística");
        logistica3.setActivo(true);
        entityManager.persistAndFlush(logistica3);

        // When
        List<LogisticaSoporte> soporteTecnico = logisticaSoporteRepository.findByAreaResponsable("Soporte Técnico");
        List<LogisticaSoporte> logisticas = logisticaSoporteRepository.findByAreaResponsable("Logística");

        // Then
        assertEquals(2, soporteTecnico.size());
        assertEquals(1, logisticas.size());
        assertEquals("Logística", logisticas.get(0).getAreaResponsable());
    }
}