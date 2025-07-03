package com.example.EduTech.ServiceTest;

import com.example.EduTech.Model.Usuarios.Estudiante;
import com.example.EduTech.Repository.EstudianteRepository;
import com.example.EduTech.Service.EstudianteService;
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
class EstudianteServiceTest {

    @Mock
    private EstudianteRepository estudianteRepository;

    @InjectMocks
    private EstudianteService estudianteService;

    private Estudiante estudiante1;
    private Estudiante estudiante2;

    @BeforeEach
    void setUp() {
        estudiante1 = new Estudiante();
        estudiante1.setId(1);
        estudiante1.setNombreUsuario("Juan Perez");
        estudiante1.setCorreo("juan@example.com");
        estudiante1.setNivelAcademico("Primer año");

        estudiante2 = new Estudiante();
        estudiante2.setId(2);
        estudiante2.setNombreUsuario("Maria Garcia");
        estudiante2.setCorreo("maria@example.com");
        estudiante2.setNivelAcademico("Segundo año");
    }

    @Test
    void crearEstudiante_DebeGuardarYRetornarEstudiante() {
        // Arrange
        when(estudianteRepository.save(any(Estudiante.class))).thenReturn(estudiante1);

        // Act
        Estudiante resultado = estudianteService.crearEstudiante(estudiante1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Juan Perez", resultado.getNombreUsuario());
        verify(estudianteRepository, times(1)).save(estudiante1);
    }

    @Test
    void actualizarEstudiante_DebeActualizarYRetornarEstudiante() {
        // Arrange
        when(estudianteRepository.save(any(Estudiante.class))).thenReturn(estudiante1);

        // Act
        Estudiante resultado = estudianteService.actualizarEstudiante(estudiante1);

        // Assert
        assertNotNull(resultado);
        verify(estudianteRepository, times(1)).save(estudiante1);
    }

    @Test
    void eliminarEstudiante_DebeEliminarEstudianteExistente() {
        // Arrange - no es necesario when() porque deleteById es void

        // Act
        estudianteService.eliminarEstudiante(1);

        // Assert
        verify(estudianteRepository, times(1)).deleteById(1);
    }

    @Test
    void listarEstudiantes_DebeRetornarTodosLosEstudiantes() {
        // Arrange
        when(estudianteRepository.findAll()).thenReturn(Arrays.asList(estudiante1, estudiante2));

        // Act
        List<Estudiante> resultado = estudianteService.listarEstudiantes();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(estudianteRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_DebeRetornarEstudianteCuandoExiste() {
        // Arrange
        when(estudianteRepository.findById(1)).thenReturn(Optional.of(estudiante1));

        // Act
        Optional<Estudiante> resultado = estudianteService.obtenerPorId(1);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Juan Perez", resultado.get().getNombreUsuario());
        verify(estudianteRepository, times(1)).findById(1);
    }

    @Test
    void obtenerPorId_DebeRetornarVacioCuandoNoExiste() {
        // Arrange
        when(estudianteRepository.findById(99)).thenReturn(Optional.empty());

        // Act
        Optional<Estudiante> resultado = estudianteService.obtenerPorId(99);

        // Assert
        assertFalse(resultado.isPresent());
        verify(estudianteRepository, times(1)).findById(99);
    }

    @Test
    void obtenerNivelAcademico_DebeRetornarNivelCuandoEstudianteExiste() {
        // Arrange
        when(estudianteRepository.findById(1)).thenReturn(Optional.of(estudiante1));

        // Act
        String resultado = estudianteService.obtenerNivelAcademico(1);

        // Assert
        assertEquals("Primer año", resultado);
        verify(estudianteRepository, times(1)).findById(1);
    }

    @Test
    void obtenerNivelAcademico_DebeRetornarMensajeCuandoEstudianteNoExiste() {
        // Arrange
        when(estudianteRepository.findById(99)).thenReturn(Optional.empty());

        // Act
        String resultado = estudianteService.obtenerNivelAcademico(99);

        // Assert
        assertEquals("Estudiante no encontrado", resultado);
        verify(estudianteRepository, times(1)).findById(99);
    }

    // Prueba adicional para verificar el comportamiento con campos nulos
    @Test
    void crearEstudiante_DebeManejarCamposNulos() {
        // Arrange
        Estudiante estudianteNulo = new Estudiante();
        estudianteNulo.setId(3);
        when(estudianteRepository.save(any(Estudiante.class))).thenReturn(estudianteNulo);

        // Act
        Estudiante resultado = estudianteService.crearEstudiante(estudianteNulo);

        // Assert
        assertNotNull(resultado);
        assertNull(resultado.getNombreUsuario());
        assertNull(resultado.getCorreo());
        assertNull(resultado.getNivelAcademico());
    }
}