package com.example.EduTech.ControllerTest;

import com.example.EduTech.Assembler.EvaluacionModelAssembler;
import com.example.EduTech.Controller.EvaluacionController;
import com.example.EduTech.Model.Curso.Evaluacion;
import com.example.EduTech.Service.EvaluacionService;
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

class EvaluacionControllerTest {

    @Mock
    private EvaluacionService service;

    @Mock
    private EvaluacionModelAssembler assembler;

    @InjectMocks
    private EvaluacionController controller;

    private Evaluacion evaluacion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        evaluacion = new Evaluacion();
        evaluacion.setId(1);
        evaluacion.setMateria("Evaluación 1");
    }

    @Test
    void crear_ReturnsCreated_WhenEvaluacionIsCreated() {
        when(service.crearEvaluacion(any(Evaluacion.class))).thenReturn(evaluacion);
        when(assembler.toModel(any(Evaluacion.class))).thenReturn(EntityModel.of(evaluacion));

        ResponseEntity<EntityModel<Evaluacion>> response = controller.crear(evaluacion);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service, times(1)).crearEvaluacion(any(Evaluacion.class));
    }

    @Test
    void actualizar_ReturnsUpdatedEvaluacion_WhenEvaluacionIsUpdated() {
        when(service.actualizarEvaluacion(any(Evaluacion.class))).thenReturn(evaluacion);
        when(assembler.toModel(any(Evaluacion.class))).thenReturn(EntityModel.of(evaluacion));

        ResponseEntity<EntityModel<Evaluacion>> response = controller.actualizar(evaluacion);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service, times(1)).actualizarEvaluacion(any(Evaluacion.class));
    }

    @Test
    void eliminar_ReturnsSuccessMessage_WhenEvaluacionIsDeleted() {
        doNothing().when(service).eliminarEvaluacion(1);

        ResponseEntity<String> response = controller.eliminar(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Evaluación eliminada", response.getBody());
        verify(service, times(1)).eliminarEvaluacion(1);
    }

    @Test
    void listar_ReturnsEvaluacionList_WhenEvaluacionesExist() {
        List<Evaluacion> evaluaciones = Arrays.asList(evaluacion);
        when(service.listarEvaluaciones()).thenReturn(evaluaciones);
        when(assembler.toCollectionModel(any())).thenReturn(CollectionModel.of(Arrays.asList(EntityModel.of(evaluacion))));

        ResponseEntity<CollectionModel<EntityModel<Evaluacion>>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    void listar_ReturnsNoContent_WhenNoEvaluacionesExist() {
        when(service.listarEvaluaciones()).thenReturn(Collections.emptyList());

        ResponseEntity<CollectionModel<EntityModel<Evaluacion>>> response = controller.listar();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void obtenerPorId_ReturnsEvaluacion_WhenEvaluacionExists() {
        when(service.obtenerEvaluacionPorId(1)).thenReturn(Optional.of(evaluacion));
        when(assembler.toModel(any(Evaluacion.class))).thenReturn(EntityModel.of(evaluacion));

        ResponseEntity<EntityModel<Evaluacion>> response = controller.obtenerPorId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(evaluacion, response.getBody().getContent());
    }

    @Test
    void obtenerPorId_ReturnsNotFound_WhenEvaluacionDoesNotExist() {
        when(service.obtenerEvaluacionPorId(anyInt())).thenReturn(Optional.empty());

        ResponseEntity<EntityModel<Evaluacion>> response = controller.obtenerPorId(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
