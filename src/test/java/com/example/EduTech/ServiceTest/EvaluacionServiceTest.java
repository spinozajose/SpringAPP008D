package com.example.EduTech.ServiceTest;

import com.example.EduTech.Model.Curso.Evaluacion;
import com.example.EduTech.Repository.EvaluacionRepository;
import com.example.EduTech.Service.EvaluacionService;
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
class EvaluacionServiceTest {

    @Mock
    private EvaluacionRepository evaluacionRepository;

    @InjectMocks
    private EvaluacionService evaluacionService;

    private Evaluacion evaluacion1;
    private Evaluacion evaluacion2;

    @BeforeEach
    void setUp() {
        evaluacion1 = new Evaluacion();
        evaluacion1.setId(1);
        evaluacion1.setMateria("Matemáticas");
        evaluacion1.setNota(6.5); // Nota dentro del rango válido

        evaluacion2 = new Evaluacion();
        evaluacion2.setId(2);
        evaluacion2.setMateria("Historia");
        evaluacion2.setNota(4.0); // Nota dentro del rango válido
    }

    @Test
    void crearEvaluacion_DebeGuardarCuandoNotaEstaEnRangoValido() {
        // Arrange
        when(evaluacionRepository.save(any(Evaluacion.class))).thenReturn(evaluacion1);

        // Act
        Evaluacion resultado = evaluacionService.crearEvaluacion(evaluacion1);

        // Assert
        assertNotNull(resultado);
        assertEquals(6.5, resultado.getNota(), 0.001);
        verify(evaluacionRepository, times(1)).save(evaluacion1);
    }

    @Test
    void crearEvaluacion_DebeLanzarExcepcionCuandoNotaEsMenorACero() {
        // Arrange
        Evaluacion evalNotaInvalida = new Evaluacion();
        evalNotaInvalida.setMateria("Física");
        evalNotaInvalida.setNota(-1.0);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            evaluacionService.crearEvaluacion(evalNotaInvalida);
        });
        verify(evaluacionRepository, never()).save(any());
    }

    @Test
    void crearEvaluacion_DebeLanzarExcepcionCuandoNotaEsMayorASiete() {
        // Arrange
        Evaluacion evalNotaInvalida = new Evaluacion();
        evalNotaInvalida.setMateria("Química");
        evalNotaInvalida.setNota(7.1);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            evaluacionService.crearEvaluacion(evalNotaInvalida);
        });
        verify(evaluacionRepository, never()).save(any());
    }

    @Test
    void actualizarEvaluacion_DebeActualizarCuandoNotaEstaEnRangoValido() {
        // Arrange
        when(evaluacionRepository.save(any(Evaluacion.class))).thenReturn(evaluacion1);

        // Act
        Evaluacion resultado = evaluacionService.actualizarEvaluacion(evaluacion1);

        // Assert
        assertNotNull(resultado);
        verify(evaluacionRepository, times(1)).save(evaluacion1);
    }

    @Test
    void actualizarEvaluacion_DebeLanzarExcepcionCuandoNotaEsInvalida() {
        // Arrange
        Evaluacion evalNotaInvalida = new Evaluacion();
        evalNotaInvalida.setId(1);
        evalNotaInvalida.setMateria("Biología");
        evalNotaInvalida.setNota(8.0);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            evaluacionService.actualizarEvaluacion(evalNotaInvalida);
        });
        verify(evaluacionRepository, never()).save(any());
    }

    @Test
    void eliminarEvaluacion_DebeEliminarCorrectamente() {
        // Act
        evaluacionService.eliminarEvaluacion(1);

        // Assert
        verify(evaluacionRepository, times(1)).deleteById(1);
    }

    @Test
    void listarEvaluaciones_DebeRetornarTodasLasEvaluaciones() {
        // Arrange
        when(evaluacionRepository.findAll()).thenReturn(Arrays.asList(evaluacion1, evaluacion2));

        // Act
        List<Evaluacion> resultado = evaluacionService.listarEvaluaciones();

        // Assert
        assertEquals(2, resultado.size());
        verify(evaluacionRepository, times(1)).findAll();
    }

    @Test
    void obtenerEvaluacionPorId_DebeRetornarEvaluacionExistente() {
        // Arrange
        when(evaluacionRepository.findById(1)).thenReturn(Optional.of(evaluacion1));

        // Act
        Optional<Evaluacion> resultado = evaluacionService.obtenerEvaluacionPorId(1);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Matemáticas", resultado.get().getMateria());
    }

    @Test
    void obtenerEvaluacionPorId_DebeRetornarVacioParaIdInexistente() {
        // Arrange
        when(evaluacionRepository.findById(99)).thenReturn(Optional.empty());

        // Act
        Optional<Evaluacion> resultado = evaluacionService.obtenerEvaluacionPorId(99);

        // Assert
        assertFalse(resultado.isPresent());
    }
}