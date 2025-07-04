package com.example.EduTech.ControllerTest;

import com.example.EduTech.Assembler.AdminSistemaModelAssembler;
import com.example.EduTech.Controller.AdminSistemaController;
import com.example.EduTech.Model.Usuarios.AdminSistema;
import com.example.EduTech.Service.AdminSistemaService;
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

class AdminSistemaControllerTest {

    @Mock
    private AdminSistemaService service;

    @Mock
    private AdminSistemaModelAssembler assembler;

    @InjectMocks
    private AdminSistemaController controller;

    private AdminSistema adminSistema;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adminSistema = new AdminSistema();
        adminSistema.setId(1);
        adminSistema.setNombreUsuario("Admin Juan");
    }

    @Test
    void crear_ReturnsCreated_WhenAdminIsCreated() {
        when(service.crear(any(AdminSistema.class))).thenReturn(adminSistema);
        when(assembler.toModel(any(AdminSistema.class))).thenReturn(EntityModel.of(adminSistema));

        ResponseEntity<EntityModel<AdminSistema>> response = controller.crear(adminSistema);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service, times(1)).crear(any(AdminSistema.class));
    }

    @Test
    void actualizar_ReturnsUpdatedAdmin_WhenAdminIsUpdated() {
        when(service.actualizar(any(AdminSistema.class))).thenReturn(adminSistema);
        when(assembler.toModel(any(AdminSistema.class))).thenReturn(EntityModel.of(adminSistema));

        ResponseEntity<EntityModel<AdminSistema>> response = controller.actualizar(adminSistema);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service, times(1)).actualizar(any(AdminSistema.class));
    }

    @Test
    void eliminar_ReturnsOk_WhenAdminIsDeleted() {
        when(service.eliminar(1)).thenReturn(true);

        ResponseEntity<String> response = controller.eliminar(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Eliminado con éxito", response.getBody());
        verify(service, times(1)).eliminar(1);
    }

    @Test
    void eliminar_ReturnsNotFound_WhenAdminDoesNotExist() {
        when(service.eliminar(1)).thenReturn(false);

        ResponseEntity<String> response = controller.eliminar(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No se encontró el administrador", response.getBody());
    }

    @Test
    void listar_ReturnsAdminList_WhenAdminsExist() {
        when(service.listar()).thenReturn(Arrays.asList(adminSistema));
        when(assembler.toCollectionModel(any())).thenReturn(CollectionModel.of(Arrays.asList(EntityModel.of(adminSistema))));

        ResponseEntity<CollectionModel<EntityModel<AdminSistema>>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    void listar_ReturnsNoContent_WhenNoAdminsExist() {
        when(service.listar()).thenReturn(Collections.emptyList());

        ResponseEntity<CollectionModel<EntityModel<AdminSistema>>> response = controller.listar();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void obtenerPorId_ReturnsAdmin_WhenAdminExists() {
        when(service.obtenerPorId(1)).thenReturn(Optional.of(adminSistema));
        when(assembler.toModel(any(AdminSistema.class))).thenReturn(EntityModel.of(adminSistema));

        ResponseEntity<EntityModel<AdminSistema>> response = controller.obtenerPorId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(adminSistema, response.getBody().getContent());
        verify(service, times(1)).obtenerPorId(1);
    }

    @Test
    void obtenerPorId_ReturnsNotFound_WhenAdminDoesNotExist() {
        when(service.obtenerPorId(anyInt())).thenReturn(Optional.empty());

        ResponseEntity<EntityModel<AdminSistema>> response = controller.obtenerPorId(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void desactivar_ReturnsOk_WhenAdminIsDeactivated() {
        when(service.desactivar(1)).thenReturn(true);

        ResponseEntity<String> response = controller.desactivar(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Administrador desactivado", response.getBody());
        verify(service, times(1)).desactivar(1);
    }

    @Test
    void desactivar_ReturnsNotFound_WhenAdminDoesNotExist() {
        when(service.desactivar(1)).thenReturn(false);

        ResponseEntity<String> response = controller.desactivar(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No se encontró el administrador", response.getBody());
    }
}
