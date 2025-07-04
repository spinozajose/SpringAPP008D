package com.example.EduTech.ControllerTest;

import com.example.EduTech.Assembler.EstudianteModelAssembler;
import com.example.EduTech.Controller.EstudianteController;
import com.example.EduTech.Model.Usuarios.Estudiante;
import com.example.EduTech.Service.EstudianteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class EstudianteControllerTest {

    @Mock
    private EstudianteService service;

    @Mock
    private EstudianteModelAssembler assembler;

    @InjectMocks
    private EstudianteController controller;

    private Estudiante estudiante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        estudiante = new Estudiante();
        estudiante.setId(1);
        estudiante.setNombreUsuario("Pedro Martínez");
    }

    @Test
    void crear_ReturnsCreated_WhenEstudianteIsCreated() {
        when(service.crearEstudiante(any(Estudiante.class))).thenReturn(estudiante);
        when(assembler.toModel(any(Estudiante.class))).thenReturn(EntityModel.of(estudiante));

        ResponseEntity<EntityModel<Estudiante>> response = controller.crear(estudiante);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service, times(1)).crearEstudiante(any(Estudiante.class));
    }

    @Test
    void actualizar_ReturnsUpdatedEstudiante_WhenEstudianteIsUpdated() {
        when(service.actualizarEstudiante(any(Estudiante.class))).thenReturn(estudiante);
        when(assembler.toModel(any(Estudiante.class))).thenReturn(EntityModel.of(estudiante));

        ResponseEntity<EntityModel<Estudiante>> response = controller.actualizar(estudiante);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service, times(1)).actualizarEstudiante(any(Estudiante.class));
    }

    @Test
    void eliminar_ReturnsSuccessMessage_WhenEstudianteIsDeleted() {
        doNothing().when(service).eliminarEstudiante(1);

        ResponseEntity<String> response = controller.eliminar(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Estudiante eliminado", response.getBody());
        verify(service, times(1)).eliminarEstudiante(1);
    }

    @Test
    void listar_ReturnsEstudianteList_WhenEstudiantesExist() {
        List<Estudiante> estudiantes = Arrays.asList(estudiante);
        when(service.listarEstudiantes()).thenReturn(estudiantes);
        when(assembler.toCollectionModel(any())).thenReturn(CollectionModel.of(Arrays.asList(EntityModel.of(estudiante))));

        ResponseEntity<CollectionModel<EntityModel<Estudiante>>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    void listar_ReturnsNoContent_WhenNoEstudiantesExist() {
        when(service.listarEstudiantes()).thenReturn(Collections.emptyList());

        ResponseEntity<CollectionModel<EntityModel<Estudiante>>> response = controller.listar();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void obtenerPorId_ReturnsEstudiante_WhenEstudianteExists() {
        when(service.obtenerPorId(1)).thenReturn(Optional.of(estudiante));
        when(assembler.toModel(any(Estudiante.class))).thenReturn(EntityModel.of(estudiante));

        ResponseEntity<EntityModel<Estudiante>> response = controller.obtenerPorId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(estudiante, response.getBody().getContent());
    }

    @Test
    void obtenerPorId_ReturnsNotFound_WhenEstudianteDoesNotExist() {
        when(service.obtenerPorId(anyInt())).thenReturn(Optional.empty());

        ResponseEntity<EntityModel<Estudiante>> response = controller.obtenerPorId(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void obtenerNivelAcademico_ReturnsNivel_WhenEstudianteExists() {
        when(service.obtenerNivelAcademico(1)).thenReturn("Segundo año");

        ResponseEntity<String> response = controller.obtenerNivelAcademico(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Segundo año", response.getBody());
        verify(service, times(1)).obtenerNivelAcademico(1);
    }

    @Test
    void obtenerNivelAcademico_ReturnsNotFound_WhenEstudianteDoesNotExist() {
        when(service.obtenerNivelAcademico(1)).thenReturn(null);

        ResponseEntity<String> response = controller.obtenerNivelAcademico(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
