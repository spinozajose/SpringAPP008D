package com.example.EduTech.Controller;

import com.example.EduTech.Assembler.AdminSistemaModelAssembler;
import com.example.EduTech.Model.Usuarios.AdminSistema;
import com.example.EduTech.Service.AdminSistemaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/admin-sistema")
@Tag(name = "Controlador de Administradores del Sistema", description = "Gestión de administradores del sistema")
public class AdminSistemaController {

    @Autowired
    private AdminSistemaService service;

    @Autowired
    private AdminSistemaModelAssembler assembler;

    @PostMapping("/crear")
    @Operation(summary = "Crear administrador", description = "Registra un nuevo administrador en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Administrador creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdminSistema.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<EntityModel<AdminSistema>> crear(@RequestBody AdminSistema admin) {
        AdminSistema nuevoAdmin = service.crear(admin);
        return new ResponseEntity<>(assembler.toModel(nuevoAdmin), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar")
    @Operation(summary = "Actualizar administrador", description = "Modifica los datos de un administrador existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Administrador actualizado"),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    public ResponseEntity<EntityModel<AdminSistema>> actualizar(@RequestBody AdminSistema admin) {
        AdminSistema adminActualizado = service.actualizar(admin);
        return ResponseEntity.ok(assembler.toModel(adminActualizado));
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar administrador", description = "Elimina un administrador del sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Administrador eliminado",
                    content = @Content(schema = @Schema(example = "Eliminado con éxito"))),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado",
                    content = @Content(schema = @Schema(example = "No se encontró el administrador")))
    })
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        boolean resultado = service.eliminar(id);
        return resultado ?
                ResponseEntity.ok("Eliminado con éxito") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el administrador");
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar administradores", description = "Obtiene todos los administradores registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de administradores obtenida"),
            @ApiResponse(responseCode = "204", description = "No hay administradores registrados")
    })
    public ResponseEntity<CollectionModel<EntityModel<AdminSistema>>> listar() {
        List<AdminSistema> admins = service.listar();
        if(admins.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(admins), HttpStatus.OK);
    }

    @GetMapping("/obtener/{id}")
    @Operation(summary = "Obtener administrador por ID", description = "Recupera un administrador específico por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Administrador encontrado"),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    public ResponseEntity<EntityModel<AdminSistema>> obtenerPorId(@PathVariable int id) {
        return service.obtenerPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/desactivar/{id}")
    @Operation(summary = "Desactivar administrador", description = "Desactiva temporalmente un administrador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Administrador desactivado",
                    content = @Content(schema = @Schema(example = "Administrador desactivado"))),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado",
                    content = @Content(schema = @Schema(example = "No se encontró el administrador")))
    })
    public ResponseEntity<String> desactivar(@PathVariable int id) {
        boolean resultado = service.desactivar(id);
        return resultado ?
                ResponseEntity.ok("Administrador desactivado") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el administrador");
    }
}
