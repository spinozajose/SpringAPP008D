package com.example.EduTech.Controller;

import com.example.EduTech.Assembler.LogisticaSoporteModelAssembler;
import com.example.EduTech.Model.Usuarios.LogisticaSoporte;
import com.example.EduTech.Service.LogisticaSoporteService;
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
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/logistica")
@CrossOrigin(origins = "*")
@Tag(name = "Controlador de Logística y Soporte", description = "Gestión del personal de logística y soporte técnico")
public class LogisticaSoporteController {

    @Autowired
    private LogisticaSoporteService servicio;

    @Autowired
    private LogisticaSoporteModelAssembler assembler;

    @GetMapping("/prueba")
    @Operation(summary = "Prueba de conexión", description = "Verifica que el endpoint está activo")
    @ApiResponse(responseCode = "200", description = "API funcionando",
            content = @Content(schema = @Schema(example = "API de logística activa")))
    public ResponseEntity<String> prueba() {
        return ResponseEntity.ok("API de logística activa");
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todo el personal", description = "Obtiene todos los registros de logística y soporte")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay registros disponibles")
    })
    public ResponseEntity<CollectionModel<EntityModel<LogisticaSoporte>>> listarTodos() {
        List<LogisticaSoporte> personal = servicio.obtenerTodos();
        if(personal.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(personal), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener por ID", description = "Recupera un miembro del personal por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro encontrado"),
            @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    public ResponseEntity<EntityModel<LogisticaSoporte>> obtenerPorId(@PathVariable int id) {
        return servicio.obtenerPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/crear")
    @Operation(summary = "Crear nuevo registro", description = "Agrega un nuevo miembro al personal de logística/soporte")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<EntityModel<LogisticaSoporte>> crear(@RequestBody LogisticaSoporte logistica) {
        LogisticaSoporte nuevoRegistro = servicio.crear(logistica);
        return new ResponseEntity<>(assembler.toModel(nuevoRegistro), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar registro", description = "Actualiza los datos de un miembro existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro actualizado"),
            @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    public ResponseEntity<EntityModel<LogisticaSoporte>> actualizar(
            @PathVariable int id, @RequestBody LogisticaSoporte logistica) {
        LogisticaSoporte actualizado = servicio.actualizar(id, logistica);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar registro", description = "Elimina un miembro del personal de logística/soporte")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Registro eliminado"),
            @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}