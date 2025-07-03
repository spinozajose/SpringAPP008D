package com.example.EduTech.Controller;

import com.example.EduTech.Assembler.CursoModelAssembler;
import com.example.EduTech.Model.Curso.Curso;
import com.example.EduTech.Service.CursoService;
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
@RequestMapping("/cursos")
@Tag(name = "Controlador de Cursos", description = "Gestión de cursos educativos")
public class CursoController {

    @Autowired
    private CursoService service;

    @Autowired
    private CursoModelAssembler assembler;

    @PostMapping("/crear")
    @Operation(summary = "Crear curso", description = "Registra un nuevo curso en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Curso creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Curso.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<EntityModel<Curso>> crear(@RequestBody Curso curso) {
        Curso nuevoCurso = service.crearCurso(curso);
        return new ResponseEntity<>(assembler.toModel(nuevoCurso), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar")
    @Operation(summary = "Actualizar curso", description = "Modifica los datos de un curso existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso actualizado"),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    public ResponseEntity<EntityModel<Curso>> actualizar(@RequestBody Curso curso) {
        Curso cursoActualizado = service.actualizarCurso(curso);
        return ResponseEntity.ok(assembler.toModel(cursoActualizado));
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar curso", description = "Elimina un curso del sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso eliminado",
                    content = @Content(schema = @Schema(example = "Curso eliminado"))),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        service.eliminarCurso(id);
        return ResponseEntity.ok("Curso eliminado");
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar cursos", description = "Obtiene todos los cursos registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de cursos obtenida"),
            @ApiResponse(responseCode = "204", description = "No hay cursos registrados")
    })
    public ResponseEntity<CollectionModel<EntityModel<Curso>>> listar() {
        List<Curso> cursos = service.listarCursos();
        if(cursos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(cursos), HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar curso por ID", description = "Recupera un curso específico por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso encontrado"),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    public ResponseEntity<EntityModel<Curso>> obtenerPorId(@PathVariable int id) {
        return service.obtenerCursoPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}