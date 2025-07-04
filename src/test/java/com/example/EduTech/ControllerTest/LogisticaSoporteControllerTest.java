package com.example.EduTech.ControllerTest;



import com.example.EduTech.Assembler.LogisticaSoporteModelAssembler;
import com.example.EduTech.Controller.LogisticaSoporteController;
import com.example.EduTech.Model.Usuarios.LogisticaSoporte;
import com.example.EduTech.Service.LogisticaSoporteService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class LogisticaSoporteControllerTest {

    @Mock
    private LogisticaSoporteService servicio;

    @Mock
    private LogisticaSoporteModelAssembler assembler;

    @InjectMocks
    private LogisticaSoporteController controller;

    private LogisticaSoporte logisticaSoporte;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        logisticaSoporte = new LogisticaSoporte();
        logisticaSoporte.setId(1);
        logisticaSoporte.setNombreUsuario("Juan Perez");
        logisticaSoporte.setAreaResponsable("Soporte Técnico");
    }

    @Test
    void prueba_ReturnsApiActiveMessage() {
        ResponseEntity<String> response = controller.prueba();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("API de logística activa", response.getBody());
    }

    @Test
    void listarTodos_ReturnsPersonalList_WhenPersonalExists() {
        when(servicio.obtenerTodos()).thenReturn(Arrays.asList(logisticaSoporte));
        when(assembler.toCollectionModel(any())).thenReturn(CollectionModel.of(
                Arrays.asList(EntityModel.of(logisticaSoporte))));

        ResponseEntity<CollectionModel<EntityModel<LogisticaSoporte>>> response = controller.listarTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    void listarTodos_ReturnsNoContent_WhenNoPersonalExists() {
        when(servicio.obtenerTodos()).thenReturn(Collections.emptyList());

        ResponseEntity<CollectionModel<EntityModel<LogisticaSoporte>>> response = controller.listarTodos();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void obtenerPorId_ReturnsPersonal_WhenPersonalExists() {
        when(servicio.obtenerPorId(1)).thenReturn(Optional.of(logisticaSoporte));
        when(assembler.toModel(any(LogisticaSoporte.class))).thenReturn(EntityModel.of(logisticaSoporte));

        ResponseEntity<EntityModel<LogisticaSoporte>> response = controller.obtenerPorId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(logisticaSoporte, response.getBody().getContent());
    }

    @Test
    void obtenerPorId_ReturnsNotFound_WhenPersonalDoesNotExist() {
        when(servicio.obtenerPorId(anyInt())).thenReturn(Optional.empty());

        ResponseEntity<EntityModel<LogisticaSoporte>> response = controller.obtenerPorId(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void crear_ReturnsCreated_WhenPersonalIsCreated() {
        when(servicio.crear(any(LogisticaSoporte.class))).thenReturn(logisticaSoporte);
        when(assembler.toModel(any(LogisticaSoporte.class))).thenReturn(EntityModel.of(logisticaSoporte));

        ResponseEntity<EntityModel<LogisticaSoporte>> response = controller.crear(logisticaSoporte);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(servicio, times(1)).crear(any(LogisticaSoporte.class));
    }

    @Test
    void actualizar_ReturnsUpdatedPersonal_WhenPersonalIsUpdated() {
        when(servicio.actualizar(eq(1), any(LogisticaSoporte.class))).thenReturn(logisticaSoporte);
        when(assembler.toModel(any(LogisticaSoporte.class))).thenReturn(EntityModel.of(logisticaSoporte));

        ResponseEntity<EntityModel<LogisticaSoporte>> response = controller.actualizar(1, logisticaSoporte);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(servicio, times(1)).actualizar(eq(1), any(LogisticaSoporte.class));
    }

    @Test
    void eliminar_ReturnsNoContent_WhenPersonalIsDeleted() {
        doNothing().when(servicio).eliminar(1);

        ResponseEntity<Void> response = controller.eliminar(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(servicio, times(1)).eliminar(1);
    }
}
