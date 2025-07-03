package com.example.EduTech.ServiceTest;

import com.example.EduTech.Model.Usuarios.Usuario;
import com.example.EduTech.Repository.UsuarioRepository;
import com.example.EduTech.Service.UsuarioService;
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
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository; // Crea un mock de UsuarioRepository (Mock = "imitación")

    @InjectMocks
    private UsuarioService usuarioService; // Crea una instancia de UsuarioService e inyecta los mocks necesarios

    private Usuario usuario1; //se declaran los usuarios de prueba
    private Usuario usuario2;

    @BeforeEach      // BeforeEach indica que el metodo anotado debe ejecutarse antes de cada metodo @Test
    void setUp() {  // Permite configurar un estado inicial para las pruebas
        usuario1 = new Usuario();
        usuario1.setId(1);
        usuario1.setNombreUsuario("Usuario1");
        usuario1.setContrasena("pass1");
        usuario1.setCorreo("user1@example.com");

        usuario2 = new Usuario();
        usuario2.setId(2);
        usuario2.setNombreUsuario("Usuario2");
        usuario2.setContrasena("pass2");
        usuario2.setCorreo("user2@example.com");
    }


    // Se siguen patron de organizamiento de las pruebas AAA (Arrange,Act,Assert) - Preparar,Ejecutar,Verificar

    // Verifica que se retorne la lista correcta de usuarios
    @Test
    void obtenerUsuarios_DebeRetornarListaDeUsuarios() {
        // Preparar
        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        // Ejecutar
        List<Usuario> resultado = usuarioService.obtenerUsuarios();

        // Assert (afirmar / verificar) comprueba los valores de retorno
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }
    // Verifica que el metodo .save del repositorio sea llamado.
    @Test
    void agregarUsuario_DebeGuardarUsuario() {
        // Arrange (Preparar)
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario1);

        // Act (Ejecutar)
        usuarioService.agregarUsuario(usuario1);

        // Assert (Verificar)
        verify(usuarioRepository, times(1)).save(usuario1);
    }
    // verifica que se retorne un usuario existente
    @Test
    void obtenerUsuarioPorId_DebeRetornarUsuarioExistente() {
        // Arrange (Preparar)
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario1));

        // Act (Ejecutar)
        Optional<Usuario> resultado = usuarioService.obtenerUsuario(1);

        // Assert (Verificar)
        assertTrue(resultado.isPresent());
        assertEquals(usuario1.getNombreUsuario(), resultado.get().getNombreUsuario());
        verify(usuarioRepository, times(1)).findById(1);
    }
    // verifica el manejo de usuarios no existentes
    @Test
    void obtenerUsuarioPorId_DebeRetornarVacioParaUsuarioInexistente() {
        // Arrange (Preparar)
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        // Act (Ejecutar)
        Optional<Usuario> resultado = usuarioService.obtenerUsuario(99);

        // Assert (Verificar)
        assertFalse(resultado.isPresent());
        verify(usuarioRepository, times(1)).findById(99);
    }
    // verifica que retorne un usuario existente
    @Test
    void eliminarUsuario_DebeEliminarUsuarioExistente() {

        // Act (Ejecutar)
        usuarioService.eliminarUsuario(1);

        // Assert (Verificar)
        verify(usuarioRepository, times(1)).deleteById(1);
    }
    // Verifica la actualización correcta de campos
    @Test
    void actualizarUsuario_DebeActualizarUsuarioExistente() {
        // Arrange (Preparar)
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario1));
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombreUsuario("nuevoNombre");
        usuarioActualizado.setContrasena("nuevaPass");
        usuarioActualizado.setCorreo("nuevo@correo.com");

        // Act (Ejecutar)
        usuarioService.actualizarUsuario(1, usuarioActualizado);

        // Assert (Verificar)
        verify(usuarioRepository, times(1)).findById(1);
        assertEquals("nuevoNombre", usuario1.getNombreUsuario());
        assertEquals("nuevaPass", usuario1.getContrasena());
        assertEquals("nuevo@correo.com", usuario1.getCorreo());
    }
    // Verifica el manejo de errores cuando el usuario no existe
    @Test
    void actualizarUsuario_DebeLanzarExcepcionCuandoUsuarioNoExiste() {
        // Arrange (Preparar)
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());
        Usuario usuarioActualizado = new Usuario();

        // Act & Assert (Ejecutar) y (Verificar)
        assertThrows(RuntimeException.class, () -> {
            usuarioService.actualizarUsuario(99, usuarioActualizado);
        });
        verify(usuarioRepository, times(1)).findById(99);
    }
}