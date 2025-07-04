package com.example.EduTech.Repository;

import com.example.EduTech.Model.Usuarios.AdminSistema;
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
public class AdminSistemaRepositoryTest {

    @Autowired
    private AdminSistemaRepository adminSistemaRepository;

    @Autowired
    private TestEntityManager entityManager;

    private AdminSistema adminPrueba;

    @BeforeEach
    void setUp() {
        // Configuración inicial para cada test
        Usuario usuarioBase = new Usuario();
        usuarioBase.setNombreUsuario("admin_user");
        usuarioBase.setCorreo("admin@example.com");
        usuarioBase.setContrasena("password123");
        usuarioBase.setRol("ADMIN_SISTEMA");
        entityManager.persist(usuarioBase);

        adminPrueba = new AdminSistema();
        adminPrueba.setNombreUsuario(usuarioBase.getNombreUsuario());
        adminPrueba.setCorreo(usuarioBase.getCorreo());
        adminPrueba.setContrasena(usuarioBase.getContrasena());
        adminPrueba.setRol(usuarioBase.getRol());
        adminPrueba.setActivo(true);

        entityManager.persistAndFlush(adminPrueba);
    }

    @Test
    void whenFindById_thenReturnAdminSistema() {
        // When
        Optional<AdminSistema> foundAdmin = adminSistemaRepository.findById(adminPrueba.getId());

        // Then
        assertTrue(foundAdmin.isPresent());
        assertEquals("admin@example.com", foundAdmin.get().getCorreo());
        assertTrue(foundAdmin.get().isActivo());
    }

    @Test
    void whenFindByNonExistingId_thenReturnEmpty() {
        // When
        Optional<AdminSistema> foundAdmin = adminSistemaRepository.findById(-1);

        // Then
        assertFalse(foundAdmin.isPresent());
    }

    @Test
    void whenSaveAdminSistema_thenAdminIsPersisted() {
        // Given
        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setNombreUsuario("nuevo_admin");
        usuarioNuevo.setCorreo("nuevo_admin@example.com");
        usuarioNuevo.setContrasena("pass123");
        usuarioNuevo.setRol("ADMIN_SISTEMA");
        entityManager.persist(usuarioNuevo);

        AdminSistema nuevoAdmin = new AdminSistema();
        nuevoAdmin.setNombreUsuario(usuarioNuevo.getNombreUsuario());
        nuevoAdmin.setCorreo(usuarioNuevo.getCorreo());
        nuevoAdmin.setContrasena(usuarioNuevo.getContrasena());
        nuevoAdmin.setRol(usuarioNuevo.getRol());
        nuevoAdmin.setActivo(true);

        // When
        AdminSistema savedAdmin = adminSistemaRepository.save(nuevoAdmin);

        // Then
        assertNotNull(savedAdmin.getId());
        assertEquals("nuevo_admin@example.com", savedAdmin.getCorreo());

        // Verify in DB
        AdminSistema dbAdmin = entityManager.find(AdminSistema.class, savedAdmin.getId());
        assertTrue(dbAdmin.isActivo());
    }

    @Test
    void whenUpdateAdminSistema_thenAdminIsUpdated() {
        // Given
        adminPrueba.setActivo(false);

        // When
        AdminSistema updatedAdmin = adminSistemaRepository.save(adminPrueba);

        // Then
        assertEquals(adminPrueba.getId(), updatedAdmin.getId());
        assertFalse(updatedAdmin.isActivo());

        // Verify in DB
        AdminSistema dbAdmin = entityManager.find(AdminSistema.class, adminPrueba.getId());
        assertFalse(dbAdmin.isActivo());
    }

    @Test
    void whenDeleteAdminSistema_thenAdminIsRemoved() {
        // When
        adminSistemaRepository.delete(adminPrueba);

        // Then
        AdminSistema deletedAdmin = entityManager.find(AdminSistema.class, adminPrueba.getId());
        assertNull(deletedAdmin);
    }

    @Test
    void whenFindAll_thenReturnAllAdmins() {
        // Given
        Usuario usuario2 = new Usuario();
        usuario2.setNombreUsuario("admin2");
        usuario2.setCorreo("admin2@example.com");
        usuario2.setContrasena("pass123");
        usuario2.setRol("ADMIN_SISTEMA");
        entityManager.persist(usuario2);

        AdminSistema admin2 = new AdminSistema();
        admin2.setNombreUsuario(usuario2.getNombreUsuario());
        admin2.setCorreo(usuario2.getCorreo());
        admin2.setContrasena(usuario2.getContrasena());
        admin2.setRol(usuario2.getRol());
        admin2.setActivo(true);
        entityManager.persistAndFlush(admin2);

        // When
        List<AdminSistema> allAdmins = adminSistemaRepository.findAll();

        // Then
        assertEquals(2, allAdmins.size());
    }

    @Test
    void whenFindByActivoTrue_thenReturnActiveAdmins() {
        // Given
        Usuario usuarioInactivo = new Usuario();
        usuarioInactivo.setNombreUsuario("admin_inactivo");
        usuarioInactivo.setCorreo("inactivo@example.com");
        usuarioInactivo.setContrasena("pass123");
        usuarioInactivo.setRol("ADMIN_SISTEMA");
        entityManager.persist(usuarioInactivo);

        AdminSistema adminInactivo = new AdminSistema();
        adminInactivo.setNombreUsuario(usuarioInactivo.getNombreUsuario());
        adminInactivo.setCorreo(usuarioInactivo.getCorreo());
        adminInactivo.setContrasena(usuarioInactivo.getContrasena());
        adminInactivo.setRol(usuarioInactivo.getRol());
        adminInactivo.setActivo(false);
        entityManager.persistAndFlush(adminInactivo);

        // When
        List<AdminSistema> adminsActivos = adminSistemaRepository.findByActivoTrue();

        // Then
        assertEquals(1, adminsActivos.size());
        assertEquals("admin@example.com", adminsActivos.get(0).getCorreo());
        assertTrue(adminsActivos.get(0).isActivo());
    }

    @Test
    void whenFindByCorreo_thenReturnAdminWithThatEmail() {
        // When
        Optional<AdminSistema> foundAdmin = adminSistemaRepository.findByCorreo("admin@example.com");

        // Then
        assertTrue(foundAdmin.isPresent());
        assertEquals(adminPrueba.getId(), foundAdmin.get().getId());
    }

    @Test
    void whenFindByNonExistingCorreo_thenReturnEmpty() {
        // When
        Optional<AdminSistema> foundAdmin = adminSistemaRepository.findByCorreo("no_existe@example.com");

        // Then
        assertFalse(foundAdmin.isPresent());
    }
}