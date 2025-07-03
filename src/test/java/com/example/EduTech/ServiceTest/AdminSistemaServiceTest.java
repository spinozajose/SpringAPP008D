package com.example.EduTech.ServiceTest;

import com.example.EduTech.Model.Usuarios.AdminSistema;
import com.example.EduTech.Repository.AdminSistemaRepository;
import com.example.EduTech.Service.AdminSistemaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminSistemaServiceTest {

    @Mock
    private AdminSistemaRepository adminSistemaRepository;

    @InjectMocks
    private AdminSistemaService adminSistemaService;

    private AdminSistema adminActivo;
    private AdminSistema adminInactivo;

    @BeforeEach
    void setUp() {
        adminActivo = new AdminSistema();
        adminActivo.setId(1);
        adminActivo.setActivo(true);

        adminInactivo = new AdminSistema();
        adminInactivo.setId(2);
        adminInactivo.setActivo(false);
    }

    @Test
    void crearAdminSistema_DeberiaRetornarAdminCreado() {
        // Arrange
        when(adminSistemaRepository.save(any(AdminSistema.class))).thenReturn(adminActivo);

        // Act
        AdminSistema resultado = adminSistemaService.crear(new AdminSistema());

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertTrue(resultado.isActivo());
        verify(adminSistemaRepository, times(1)).save(any(AdminSistema.class));
    }

    @Test
    void actualizarAdminSistema_DeberiaRetornarAdminActualizado() {
        // Arrange
        when(adminSistemaRepository.save(any(AdminSistema.class))).thenReturn(adminActivo);

        // Act
        AdminSistema resultado = adminSistemaService.actualizar(adminActivo);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(adminSistemaRepository, times(1)).save(any(AdminSistema.class));
    }

    @Test
    void eliminarAdminSistema_CuandoExiste_DeberiaRetornarTrue() {
        // Arrange
        when(adminSistemaRepository.existsById(1)).thenReturn(true);

        // Act
        boolean resultado = adminSistemaService.eliminar(1);

        // Assert
        assertTrue(resultado);
        verify(adminSistemaRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarAdminSistema_CuandoNoExiste_DeberiaRetornarFalse() {
        // Arrange
        when(adminSistemaRepository.existsById(99)).thenReturn(false);

        // Act
        boolean resultado = adminSistemaService.eliminar(99);

        // Assert
        assertFalse(resultado);
        verify(adminSistemaRepository, never()).deleteById(anyInt());
    }

    @Test
    void obtenerPorId_CuandoExiste_DeberiaRetornarAdmin() {
        // Arrange
        when(adminSistemaRepository.findById(1)).thenReturn(Optional.of(adminActivo));

        // Act
        Optional<AdminSistema> resultado = adminSistemaService.obtenerPorId(1);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getId());
    }

    @Test
    void obtenerPorId_CuandoNoExiste_DeberiaRetornarOptionalVacio() {
        // Arrange
        when(adminSistemaRepository.findById(99)).thenReturn(Optional.empty());

        // Act
        Optional<AdminSistema> resultado = adminSistemaService.obtenerPorId(99);

        // Assert
        assertFalse(resultado.isPresent());
    }

    @Test
    void listarAdmins_DeberiaRetornarListaDeAdmins() {
        // Arrange
        List<AdminSistema> admins = Arrays.asList(adminActivo, adminInactivo);
        when(adminSistemaRepository.findAll()).thenReturn(admins);

        // Act
        List<AdminSistema> resultado = adminSistemaService.listar();

        // Assert
        assertEquals(2, resultado.size());
        verify(adminSistemaRepository, times(1)).findAll();
    }

    @Test
    void desactivarAdmin_CuandoExiste_DeberiaDesactivarYRetornarTrue() {
        // Arrange
        when(adminSistemaRepository.findById(1)).thenReturn(Optional.of(adminActivo));
        when(adminSistemaRepository.save(any(AdminSistema.class))).thenReturn(adminInactivo);

        // Act
        boolean resultado = adminSistemaService.desactivar(1);

        // Assert
        assertTrue(resultado);
        assertFalse(adminActivo.isActivo());
        verify(adminSistemaRepository, times(1)).save(adminActivo);
    }

    @Test
    void desactivarAdmin_CuandoNoExiste_DeberiaRetornarFalse() {
        // Arrange
        when(adminSistemaRepository.findById(99)).thenReturn(Optional.empty());

        // Act
        boolean resultado = adminSistemaService.desactivar(99);

        // Assert
        assertFalse(resultado);
        verify(adminSistemaRepository, never()).save(any(AdminSistema.class));
    }
}