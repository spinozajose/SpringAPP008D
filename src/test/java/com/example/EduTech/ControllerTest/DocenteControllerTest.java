package com.example.EduTech.ControllerTest;

import com.example.EduTech.Assembler.DocenteModelAssembler;
import com.example.EduTech.Controller.DocenteController;
import com.example.EduTech.Model.Usuarios.Docente;
import com.example.EduTech.Service.DocenteService;
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

class DocenteControllerTest {

    @Mock
    private DocenteService service;

    @Mock
    private DocenteModelAssembler assembler;

    @InjectMocks
    private DocenteController controller;

    private Docente docente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        docente = new Docente();
        docente.setId(1);
        docente.setNombreUsuario("Carlos López");
    }

    @Test
    void crear_ReturnsCreated_WhenDocenteIsCreated() {
        when(service.crear(any(Docente.class))).thenReturn(docente);
        when(assembler.toModel(any(Docente.class))).thenReturn(EntityModel.of(docente));

        ResponseEntity<EntityModel<Docente>> response = controller.crear(docente);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service, times(1)).crear(any(Docente.class));
    }

    @Test
    void actualizar_ReturnsUpdatedDocente_WhenDocenteIsUpdated() {
        when(service.actualizar(any(Docente.class))).thenReturn(docente);
        when(assembler.toModel(any(Docente.class))).thenReturn(EntityModel.of(docente));

        ResponseEntity<EntityModel<Docente>> response = controller.actualizar(docente);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service, times(1)).actualizar(any(Docente.class));
    }

    @Test
    void eliminar_ReturnsNoContent_WhenDocenteIsDeleted() {
        doNothing().when(service).eliminar(1);

        ResponseEntity<Void> response = controller.eliminar(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).eliminar(1);
    }

    @Test
    void listar_ReturnsDocenteList_WhenDocentesExist() {
        List<Docente> docentes = Arrays.asList(docente);
        when(service.listar()).thenReturn(docentes);
        when(assembler.toCollectionModel(any())).thenReturn(CollectionModel.of(Arrays.asList(EntityModel.of(docente))));

        ResponseEntity<CollectionModel<EntityModel<Docente>>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    void listar_ReturnsNoContent_WhenNoDocentesExist() {
        when(service.listar()).thenReturn(Collections.emptyList());

        ResponseEntity<CollectionModel<EntityModel<Docente>>> response = controller.listar();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void obtenerPorId_ReturnsDocenteInfo_WhenDocenteExists() {
        when(service.obtenerPorId(1)).thenReturn("Docente: Carlos López");

        ResponseEntity<String> response = controller.obtenerPorId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Docente: Carlos López", response.getBody());
        verify(service, times(1)).obtenerPorId(1);
    }

    @Test
    void obtenerPorId_ReturnsNotFound_WhenDocenteDoesNotExist() {
        when(service.obtenerPorId(anyInt())).thenReturn(null);

        ResponseEntity<String> response = controller.obtenerPorId(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
