package com.example.EduTech.ServiceTest;

import com.example.EduTech.Model.Usuarios.LogisticaSoporte;
import com.example.EduTech.Repository.LogisticaSoporteRepository;
import com.example.EduTech.Service.LogisticaSoporteService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogisticaSoporteServiceTest {

    @Mock
    private LogisticaSoporteRepository logisticaRepo;

    @InjectMocks
    private LogisticaSoporteService logisticaService;

    private LogisticaSoporte logistica1;
    private LogisticaSoporte logistica2;

    @BeforeEach
    void setUp() {
        logistica1 = new LogisticaSoporte();
        logistica1.setId(1);
        logistica1.setNombreUsuario("logistica1");
        logistica1.setContrasena("pass1");
        logistica1.setCorreo("log1@example.com");
        logistica1.setRol("SOPORTE");
        logistica1.setAreaResponsable("INFRAESTRUCTURA");
        logistica1.setActivo(true);

        logistica2 = new LogisticaSoporte();
        logistica2.setId(2);
        logistica2.setNombreUsuario("logistica2");
        logistica2.setContrasena("pass2");
        logistica2.setCorreo("log2@example.com");
        logistica2.setRol("LOGISTICA");
        logistica2.setAreaResponsable("TRANSPORTE");
        logistica2.setActivo(false);
    }

    @Test
    void obtenerTodos_DebeRetornarListaCompleta() {
        // Arrange
        when(logisticaRepo.findAll()).thenReturn(Arrays.asList(logistica1, logistica2));

        // Act
        List<LogisticaSoporte> resultado = logisticaService.obtenerTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(logisticaRepo, times(1)).findAll();
    }

    @Test
    void obtenerPorId_DebeRetornarLogisticaExistente() {
        // Arrange
        when(logisticaRepo.findById(1)).thenReturn(Optional.of(logistica1));

        // Act
        Optional<LogisticaSoporte> resultado = logisticaService.obtenerPorId(1);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("logistica1", resultado.get().getNombreUsuario());
        verify(logisticaRepo, times(1)).findById(1);
    }

    @Test
    void obtenerPorId_DebeRetornarVacioParaIdInexistente() {
        // Arrange
        when(logisticaRepo.findById(99)).thenReturn(Optional.empty());

        // Act
        Optional<LogisticaSoporte> resultado = logisticaService.obtenerPorId(99);

        // Assert
        assertFalse(resultado.isPresent());
        verify(logisticaRepo, times(1)).findById(99);
    }

    @Test
    void crear_DebeGuardarYRetornarNuevaLogistica() {
        // Arrange
        when(logisticaRepo.save(any(LogisticaSoporte.class))).thenReturn(logistica1);

        // Act
        LogisticaSoporte resultado = logisticaService.crear(logistica1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(logisticaRepo, times(1)).save(logistica1);
    }

    @Test
    void actualizar_DebeActualizarLogisticaExistente() {
        // Arrange
        LogisticaSoporte datosActualizados = new LogisticaSoporte();
        datosActualizados.setNombreUsuario("nuevoNombre");
        datosActualizados.setContrasena("nuevaPass");
        datosActualizados.setCorreo("nuevo@correo.com");
        datosActualizados.setRol("NUEVO_ROL");
        datosActualizados.setAreaResponsable("NUEVA_AREA");
        datosActualizados.setActivo(false);

        when(logisticaRepo.findById(1)).thenReturn(Optional.of(logistica1));
        when(logisticaRepo.save(any(LogisticaSoporte.class))).thenReturn(logistica1);

        // Act
        LogisticaSoporte resultado = logisticaService.actualizar(1, datosActualizados);

        // Assert
        assertNotNull(resultado);
        assertEquals("nuevoNombre", logistica1.getNombreUsuario());
        assertEquals("nuevaPass", logistica1.getContrasena());
        assertEquals("nuevo@correo.com", logistica1.getCorreo());
        assertEquals("NUEVO_ROL", logistica1.getRol());
        assertEquals("NUEVA_AREA", logistica1.getAreaResponsable());
        assertFalse(logistica1.isActivo());
        verify(logisticaRepo, times(1)).findById(1);
        verify(logisticaRepo, times(1)).save(logistica1);
    }

    @Test
    void actualizar_DebeRetornarNullParaIdInexistente() {
        // Arrange
        when(logisticaRepo.findById(99)).thenReturn(Optional.empty());

        // Act
        LogisticaSoporte resultado = logisticaService.actualizar(99, logistica2);

        // Assert
        assertNull(resultado);
        verify(logisticaRepo, times(1)).findById(99);
        verify(logisticaRepo, never()).save(any());
    }

    @Test
    void eliminar_DebeEliminarLogisticaExistente() {
        // Act
        logisticaService.eliminar(1);

        // Assert
        verify(logisticaRepo, times(1)).deleteById(1);
    }
}