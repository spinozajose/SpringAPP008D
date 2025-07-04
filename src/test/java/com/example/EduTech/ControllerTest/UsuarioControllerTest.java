package com.example.EduTech.ControllerTest;

import com.example.EduTech.Assembler.UsuarioModelAssembler;
import com.example.EduTech.Controller.UsuarioController;
import com.example.EduTech.Model.Usuarios.Usuario;
import com.example.EduTech.Service.UsuarioService;
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

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioModelAssembler assembler;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombreUsuario("Test Usuario");
    }

    @Test
    void obtenerTodosUsuarios_ReturnsUsers_WhenUsersExist() {
        // Arrange
        when(usuarioService.obtenerUsuarios()).thenReturn(Arrays.asList(usuario));
        when(assembler.toCollectionModel(any())).thenReturn(CollectionModel.of(
                Arrays.asList(EntityModel.of(usuario)));

        // Act
        ResponseEntity<CollectionModel<EntityModel<Usuario>>> response =
                usuarioController.obtenerTodosUsuarios();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    void obtenerTodosUsuarios_ReturnsNotFound_WhenNoUsersExist() {
        // Arrange
        when(usuarioService.obtenerUsuarios()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<CollectionModel<EntityModel<Usuario>>> response =
                usuarioController.obtenerTodosUsuarios();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void obtenerUsuarioPorId_ReturnsUser_WhenUserExists() {
        // Arrange
        when(usuarioService.obtenerUsuario(1)).thenReturn(Optional.of(usuario));
        when(assembler.toModel(any(Usuario.class))).thenReturn(EntityModel.of(usuario));

        // Act
        ResponseEntity<EntityModel<Usuario>> response =
                usuarioController.obtenerUsuarioPorId(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(usuario, response.getBody().getContent());
    }

    @Test
    void obtenerUsuarioPorId_ReturnsNotFound_WhenUserDoesNotExist() {
        // Arrange
        when(usuarioService.obtenerUsuario(anyInt())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<EntityModel<Usuario>> response =
                usuarioController.obtenerUsuarioPorId(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void crearUsuario_ReturnsCreated_WhenUserIsCreated() {
        // Arrange
        when(usuarioService.obtenerUsuario(anyInt())).thenReturn(Optional.of(usuario));
        when(assembler.toModel(any(Usuario.class))).thenReturn(EntityModel.of(usuario));

        // Act
        ResponseEntity<EntityModel<Usuario>> response =
                usuarioController.crearUsuario(usuario);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(usuarioService, times(1)).agregarUsuario(any(Usuario.class));
    }

    @Test
    void crearUsuario_ReturnsNoContent_WhenUserIsNotCreated() {
        // Arrange
        when(usuarioService.obtenerUsuario(anyInt())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<EntityModel<Usuario>> response =
                usuarioController.crearUsuario(usuario);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(usuarioService, times(1)).agregarUsuario(any(Usuario.class));
    }

    @Test
    void actualizarUsuario_ReturnsOk_WhenUserExists() {
        // Arrange
        when(usuarioService.obtenerUsuario(1)).thenReturn(Optional.of(usuario));
        when(assembler.toModel(any(Usuario.class))).thenReturn(EntityModel.of(usuario));

        // Act
        ResponseEntity<EntityModel<Usuario>> response =
                usuarioController.actualizarUsuario(1, usuario);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(usuarioService, times(1)).actualizarUsuario(anyInt(), any(Usuario.class));
    }

    @Test
    void actualizarUsuario_ReturnsNotFound_WhenUserDoesNotExist() {
        // Arrange
        when(usuarioService.obtenerUsuario(anyInt())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<EntityModel<Usuario>> response =
                usuarioController.actualizarUsuario(1, usuario);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(usuarioService, never()).actualizarUsuario(anyInt(), any(Usuario.class));
    }

    @Test
    void eliminarUsuario_ReturnsOk_WhenUserExists() {
        // Arrange
        when(usuarioService.obtenerUsuario(1)).thenReturn(Optional.of(usuario));

        // Act
        ResponseEntity<Void> response = usuarioController.eliminarUsuario(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(usuarioService, times(1)).eliminarUsuario(1);
    }

    @Test
    void eliminarUsuario_ReturnsNotFound_WhenUserDoesNotExist() {
        // Arrange
        when(usuarioService.obtenerUsuario(anyInt())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = usuarioController.eliminarUsuario(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(usuarioService, never()).eliminarUsuario(anyInt());
    }
}