package com.example.EduTech.ServiceTest;

import com.example.EduTech.Model.Usuarios.Docente;
import com.example.EduTech.Repository.DocenteRepository;
import com.example.EduTech.Service.DocenteService;
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
class DocenteServiceTest {

    @Mock
    private DocenteRepository docenteRepository;

    @InjectMocks
    private DocenteService docenteService;

    private Docente docente1;
    private Docente docente2;

    @BeforeEach
    void setUp() {
        docente1 = new Docente();
        docente1.setId(1);
        docente1.setNombreUsuario("Profesor Juan");
        docente1.setCorreo("juan@edu.com");
        docente1.setEspecialidad("Matemáticas");
        docente1.setActivo(true);

        docente2 = new Docente();
        docente2.setId(2);
        docente2.setNombreUsuario("Profesora María");
        docente2.setCorreo("maria@edu.com");
        docente2.setEspecialidad("Literatura");
        docente2.setActivo(true);
    }

    @Test
    void testCrearDocente() {
        when(docenteRepository.save(docente1)).thenReturn(docente1);

        Docente resultado = docenteService.crear(docente1);

        assertNotNull(resultado);
        assertEquals(docente1, resultado);
        verify(docenteRepository, times(1)).save(docente1);
    }

    @Test
    void testActualizarDocente() {
        when(docenteRepository.save(docente2)).thenReturn(docente2);

        Docente resultado = docenteService.actualizar(docente2);

        assertNotNull(resultado);
        assertEquals(docente2, resultado);
        verify(docenteRepository, times(1)).save(docente2);
    }

    @Test
    void testEliminarDocente() {
        doNothing().when(docenteRepository).deleteById(1);

        docenteService.eliminar(1);

        verify(docenteRepository, times(1)).deleteById(1);
    }

    @Test
    void testListarDocentes() {
        List<Docente> docentes = Arrays.asList(docente1, docente2);
        when(docenteRepository.findAll()).thenReturn(docentes);

        List<Docente> resultado = docenteService.listar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.containsAll(docentes));
        verify(docenteRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPorIdExistente() {
        when(docenteRepository.findById(1)).thenReturn(Optional.of(docente1));

        String resultado = docenteService.obtenerPorId(1);

        assertNotNull(resultado);
        assertTrue(resultado.contains("ID: 1"));
        assertTrue(resultado.contains("Nombre: Profesor Juan"));
        assertTrue(resultado.contains("Correo: juan@edu.com"));
        assertTrue(resultado.contains("Especialidad: Matemáticas"));
        assertTrue(resultado.contains("Activo: true"));
        verify(docenteRepository, times(1)).findById(1);
    }

    @Test
    void testObtenerPorIdNoExistente() {
        when(docenteRepository.findById(99)).thenReturn(Optional.empty());

        String resultado = docenteService.obtenerPorId(99);

        assertEquals("No se encontró el Docente", resultado);
        verify(docenteRepository, times(1)).findById(99);
    }
}