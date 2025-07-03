package com.example.EduTech.Controller;

import com.example.EduTech.Assembler.EvaluacionModelAssembler;
import com.example.EduTech.Model.Curso.Evaluacion;
import com.example.EduTech.Service.EvaluacionService;
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
@RequestMapping("/evaluaciones")
@Tag(name = "Controlador de Evaluaciones", description = "Gestión de evaluaciones del sistema")
public class EvaluacionController {

    @Autowired
    private EvaluacionService service;

    @Autowired
    private EvaluacionModelAssembler assembler;


    @PostMapping("/crear")
    @Operation(summary = "Crear evaluación", description = "Registra una nueva evaluación en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evaluación creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Evaluacion.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<EntityModel<Evaluacion>> crear(@RequestBody Evaluacion evaluacion) {
        Evaluacion nuevaEvaluacion = service.crearEvaluacion(evaluacion);
        return new ResponseEntity<>(assembler.toModel(nuevaEvaluacion), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar")
    @Operation(summary = "Actualizar evaluación", description = "Modifica los datos de una evaluación existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evaluación actualizada"),
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
    })
    public ResponseEntity<EntityModel<Evaluacion>> actualizar(@RequestBody Evaluacion evaluacion) {
        Evaluacion evaluacionActualizada = service.actualizarEvaluacion(evaluacion);
        return ResponseEntity.ok(assembler.toModel(evaluacionActualizada));
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar evaluación", description = "Elimina una evaluación del sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evaluación eliminada",
                    content = @Content(schema = @Schema(example = "Evaluación eliminada"))),
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
    })
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        service.eliminarEvaluacion(id);
        return ResponseEntity.ok("Evaluación eliminada");
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar evaluaciones", description = "Obtiene todas las evaluaciones registradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de evaluaciones obtenida"),
            @ApiResponse(responseCode = "204", description = "No hay evaluaciones registradas")
    })
    public ResponseEntity<CollectionModel<EntityModel<Evaluacion>>> listar() {
        List<Evaluacion> evaluaciones = service.listarEvaluaciones();
        if(evaluaciones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(evaluaciones), HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar evaluación por ID", description = "Recupera una evaluación específica por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evaluación encontrada"),
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
    })
    public ResponseEntity<EntityModel<Evaluacion>> obtenerPorId(@PathVariable int id) {
        return service.obtenerEvaluacionPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}