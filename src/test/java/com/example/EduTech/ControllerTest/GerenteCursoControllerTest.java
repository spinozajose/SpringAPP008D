package com.example.EduTech.ControllerTest;

import com.example.EduTech.Assembler.GerenteCursoModelAssembler;
import com.example.EduTech.Controller.GerenteCursoController;
import com.example.EduTech.Model.Usuarios.GerenteCurso;
import com.example.EduTech.Service.GerenteCursoService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class GerenteCursoControllerTest {

    @Mock
    private GerenteCursoService service;

    @Mock
    private GerenteCursoModelAssembler assembler;

    @InjectMocks
    private GerenteCursoController controller;

    private GerenteCurso gerenteCurso;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gerenteCurso = new GerenteCurso();
        gerenteCurso.setId(1);
        gerenteCurso.setNombreUsuario("Laura Sánchez");
    }

    @Test
    void crear_ReturnsCreated_WhenGerenteIsCreated() {
        when(service.crear(any(GerenteCurso.class))).thenReturn(gerenteCurso);
        when(assembler.toModel(any(GerenteCurso.class))).thenReturn(EntityModel.of(gerenteCurso));

        ResponseEntity<EntityModel<GerenteCurso>> response = controller.crear(gerenteCurso);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service, times(1)).crear(any(GerenteCurso.class));
    }

    @Test
    void actualizar_ReturnsUpdatedGerente_WhenGerenteIsUpdated() {
        when(service.actualizar(any(GerenteCurso.class))).thenReturn(gerenteCurso);
        when(assembler.toModel(any(GerenteCurso.class))).thenReturn(EntityModel.of(gerenteCurso));

        ResponseEntity<EntityModel<GerenteCurso>> response = controller.actualizar(gerenteCurso);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service, times(1)).actualizar(any(GerenteCurso.class));
    }

    @Test
    void eliminar_ReturnsSuccessMessage_WhenGerenteExists() {
        when(service.eliminar(1)).thenReturn(true);

        ResponseEntity<String> response = controller.eliminar(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Gerente de Curso eliminado con éxito", response.getBody());
        verify(service, times(1)).eliminar(1);
    }

    @Test
    void eliminar_ReturnsNotFound_WhenGerenteDoesNotExist() {
        when(service.eliminar(1)).thenReturn(false);

        ResponseEntity<String> response = controller.eliminar(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No se encontró el gerente", response.getBody());
        verify(service, times(1)).eliminar(1);
    }

    @Test
    void listar_ReturnsGerenteList_WhenGerentesExist() {
        List<GerenteCurso> gerentes = Arrays.asList(gerenteCurso);
        when(service.listar()).thenReturn(gerentes);
        when(assembler.toCollectionModel(any())).thenReturn(CollectionModel.of(Arrays.asList(EntityModel.of(gerenteCurso))));

        ResponseEntity<CollectionModel<EntityModel<GerenteCurso>>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    void listar_ReturnsNoContent_WhenNoGerentesExist() {
        when(service.listar()).thenReturn(Collections.emptyList());

        ResponseEntity<CollectionModel<EntityModel<GerenteCurso>>> response = controller.listar();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void obtenerPorId_ReturnsGerente_WhenGerenteExists() {
        when(service.obtenerPorId(1)).thenReturn(gerenteCurso);
        when(assembler.toModel(any(GerenteCurso.class))).thenReturn(EntityModel.of(gerenteCurso));

        ResponseEntity<EntityModel<GerenteCurso>> response = controller.obtenerPorId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(gerenteCurso, response.getBody().getContent());
    }

    @Test
    void obtenerPorId_ReturnsNotFound_WhenGerenteDoesNotExist() {
        when(service.obtenerPorId(anyInt())).thenReturn(null);

        ResponseEntity<EntityModel<GerenteCurso>> response = controller.obtenerPorId(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void desactivar_ReturnsSuccessMessage_WhenGerenteExists() {
        when(service.desactivar(1)).thenReturn(true);

        ResponseEntity<String> response = controller.desactivar(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Gerente de Curso desactivado", response.getBody());
        verify(service, times(1)).desactivar(1);
    }

    @Test
    void desactivar_ReturnsNotFound_WhenGerenteDoesNotExist() {
        when(service.desactivar(1)).thenReturn(false);

        ResponseEntity<String> response = controller.desactivar(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No se encontró el gerente", response.getBody());
        verify(service, times(1)).desactivar(1);
    }
}
