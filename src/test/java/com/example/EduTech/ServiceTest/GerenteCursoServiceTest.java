package com.example.EduTech.ServiceTest;

import com.example.EduTech.Model.Usuarios.GerenteCurso;
import com.example.EduTech.Repository.GerenteCursoRepository;
import com.example.EduTech.Service.GerenteCursoService;
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
class GerenteCursoServiceTest {

    @Mock
    private GerenteCursoRepository repository;

    @InjectMocks
    private GerenteCursoService gerenteCursoService;

    private GerenteCurso gerente1;
    private GerenteCurso gerente2;

    @BeforeEach
    void setUp() {
        gerente1 = new GerenteCurso();
        gerente1.setId(1);
        gerente1.setNombreUsuario("gerente1");
        gerente1.setContrasena("pass1");
        gerente1.setCorreo("gerente1@example.com");
        gerente1.setRol("GERENTE");
        gerente1.setActivo(true);

        gerente2 = new GerenteCurso();
        gerente2.setId(2);
        gerente2.setNombreUsuario("gerente2");
        gerente2.setContrasena("pass2");
        gerente2.setCorreo("gerente2@example.com");
        gerente2.setRol("GERENTE");
        gerente2.setActivo(false);
    }

    @Test
    void crear_DebeGuardarYRetornarNuevoGerente() {
        // Arrange
        when(repository.save(any(GerenteCurso.class))).thenReturn(gerente1);

        // Act
        GerenteCurso resultado = gerenteCursoService.crear(gerente1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("gerente1", resultado.getNombreUsuario());
        verify(repository, times(1)).save(gerente1);
    }

    @Test
    void actualizar_DebeActualizarYRetornarGerente() {
        // Arrange
        when(repository.save(any(GerenteCurso.class))).thenReturn(gerente1);

        // Act
        GerenteCurso resultado = gerenteCursoService.actualizar(gerente1);

        // Assert
        assertNotNull(resultado);
        verify(repository, times(1)).save(gerente1);
    }

    @Test
    void eliminar_DebeRetornarTrueCuandoExiste() {
        // Arrange
        when(repository.existsById(1)).thenReturn(true);

        // Act
        boolean resultado = gerenteCursoService.eliminar(1);

        // Assert
        assertTrue(resultado);
        verify(repository, times(1)).existsById(1);
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_DebeRetornarFalseCuandoNoExiste() {
        // Arrange
        when(repository.existsById(99)).thenReturn(false);

        // Act
        boolean resultado = gerenteCursoService.eliminar(99);

        // Assert
        assertFalse(resultado);
        verify(repository, times(1)).existsById(99);
        verify(repository, never()).deleteById(anyInt());
    }

    @Test
    void obtenerPorId_DebeRetornarGerenteCuandoExiste() {
        // Arrange
        when(repository.findById(1)).thenReturn(Optional.of(gerente1));

        // Act
        GerenteCurso resultado = gerenteCursoService.obtenerPorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals("gerente1", resultado.getNombreUsuario());
        verify(repository, times(1)).findById(1);
    }

    @Test
    void obtenerPorId_DebeRetornarNullCuandoNoExiste() {
        // Arrange
        when(repository.findById(99)).thenReturn(Optional.empty());

        // Act
        GerenteCurso resultado = gerenteCursoService.obtenerPorId(99);

        // Assert
        assertNull(resultado);
        verify(repository, times(1)).findById(99);
    }

    @Test
    void listar_DebeRetornarTodosLosGerentes() {
        // Arrange
        when(repository.findAll()).thenReturn(Arrays.asList(gerente1, gerente2));

        // Act
        List<GerenteCurso> resultado = gerenteCursoService.listar();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void desactivar_DebeRetornarTrueYDesactivarCuandoExiste() {
        // Arrange
        when(repository.findById(1)).thenReturn(Optional.of(gerente1));
        when(repository.save(any(GerenteCurso.class))).thenReturn(gerente1);

        // Act
        boolean resultado = gerenteCursoService.desactivar(1);

        // Assert
        assertTrue(resultado);
        assertFalse(gerente1.isActivo());
        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).save(gerente1);
    }

    @Test
    void desactivar_DebeRetornarFalseCuandoNoExiste() {
        // Arrange
        when(repository.findById(99)).thenReturn(Optional.empty());

        // Act
        boolean resultado = gerenteCursoService.desactivar(99);

        // Assert
        assertFalse(resultado);
        verify(repository, times(1)).findById(99);
        verify(repository, never()).save(any());
    }
}