package com.example.EduTech.Controller;

import com.example.EduTech.Assembler.EstudianteModelAssembler;
import com.example.EduTech.Model.Usuarios.Estudiante;
import com.example.EduTech.Service.EstudianteService;
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
@RequestMapping("/estudiantes")
@Tag(name = "Controlador de Estudiantes", description = "Gestión de estudiantes del sistema")
public class EstudianteController {

    @Autowired
    private EstudianteService service;

    @Autowired
    private EstudianteModelAssembler assembler;

    @PostMapping("/crear")
    @Operation(summary = "Crear estudiante", description = "Registra un nuevo estudiante en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estudiante creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Estudiante.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<EntityModel<Estudiante>> crear(@RequestBody Estudiante estudiante) {
        Estudiante nuevoEstudiante = service.crearEstudiante(estudiante);
        return new ResponseEntity<>(assembler.toModel(nuevoEstudiante), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar")
    @Operation(summary = "Actualizar estudiante", description = "Modifica los datos de un estudiante existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estudiante actualizado"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    public ResponseEntity<EntityModel<Estudiante>> actualizar(@RequestBody Estudiante estudiante) {
        Estudiante estudianteActualizado = service.actualizarEstudiante(estudiante);
        return ResponseEntity.ok(assembler.toModel(estudianteActualizado));
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar estudiante", description = "Elimina un estudiante del sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estudiante eliminado",
                    content = @Content(schema = @Schema(example = "Estudiante eliminado"))),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        service.eliminarEstudiante(id);
        return ResponseEntity.ok("Estudiante eliminado");
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar estudiantes", description = "Obtiene todos los estudiantes registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de estudiantes obtenida"),
            @ApiResponse(responseCode = "204", description = "No hay estudiantes registrados")
    })
    public ResponseEntity<CollectionModel<EntityModel<Estudiante>>> listar() {
        List<Estudiante> estudiantes = service.listarEstudiantes();
        if(estudiantes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(estudiantes), HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar estudiante por ID", description = "Recupera un estudiante específico por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estudiante encontrado"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    public ResponseEntity<EntityModel<Estudiante>> obtenerPorId(@PathVariable int id) {
        return service.obtenerPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nivel-academico/{id}")
    @Operation(summary = "Obtener nivel académico", description = "Recupera el nivel académico de un estudiante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Nivel académico obtenido",
                    content = @Content(schema = @Schema(example = "Primer año"))),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    public ResponseEntity<String> obtenerNivelAcademico(@PathVariable int id) {
        String nivel = service.obtenerNivelAcademico(id);
        return nivel != null ?
                ResponseEntity.ok(nivel) :
                ResponseEntity.notFound().build();
    }
}