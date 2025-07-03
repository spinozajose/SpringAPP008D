package com.example.EduTech.Controller;

import com.example.EduTech.Assembler.GerenteCursoModelAssembler;
import com.example.EduTech.Model.Usuarios.GerenteCurso;
import com.example.EduTech.Service.GerenteCursoService;
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
@RequestMapping("/gerentecurso")
@Tag(name = "Controlador de Gerentes de Curso", description = "Gestión de gerentes de curso del sistema")
public class GerenteCursoController {

    @Autowired
    private GerenteCursoService service;

    @Autowired
    private GerenteCursoModelAssembler assembler;

    @PostMapping("/crear")
    @Operation(summary = "Crear gerente de curso", description = "Registra un nuevo gerente de curso en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Gerente de curso creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GerenteCurso.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<EntityModel<GerenteCurso>> crear(@RequestBody GerenteCurso gerenteCurso) {
        GerenteCurso nuevoGerente = service.crear(gerenteCurso);
        return new ResponseEntity<>(assembler.toModel(nuevoGerente), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar")
    @Operation(summary = "Actualizar gerente de curso", description = "Modifica los datos de un gerente de curso existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gerente de curso actualizado"),
            @ApiResponse(responseCode = "404", description = "Gerente de curso no encontrado")
    })
    public ResponseEntity<EntityModel<GerenteCurso>> actualizar(@RequestBody GerenteCurso gerenteCurso) {
        GerenteCurso gerenteActualizado = service.actualizar(gerenteCurso);
        return ResponseEntity.ok(assembler.toModel(gerenteActualizado));
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar gerente de curso", description = "Elimina un gerente de curso del sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gerente de curso eliminado",
                    content = @Content(schema = @Schema(example = "Gerente de Curso eliminado con éxito"))),
            @ApiResponse(responseCode = "404", description = "Gerente de curso no encontrado",
                    content = @Content(schema = @Schema(example = "No se encontró el gerente")))
    })
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        boolean resultado = service.eliminar(id);
        return resultado ?
                ResponseEntity.ok("Gerente de Curso eliminado con éxito") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el gerente");
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar gerentes de curso", description = "Obtiene todos los gerentes de curso registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de gerentes de curso obtenida"),
            @ApiResponse(responseCode = "204", description = "No hay gerentes de curso registrados")
    })
    public ResponseEntity<CollectionModel<EntityModel<GerenteCurso>>> listar() {
        List<GerenteCurso> gerentes = service.listar();
        if(gerentes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(gerentes), HttpStatus.OK);
    }

    @GetMapping("/obtener/{id}")
    @Operation(summary = "Obtener gerente de curso por ID", description = "Recupera un gerente de curso específico por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gerente de curso encontrado"),
            @ApiResponse(responseCode = "404", description = "Gerente de curso no encontrado")
    })
    public ResponseEntity<EntityModel<GerenteCurso>> obtenerPorId(@PathVariable int id) {
        GerenteCurso gerente = service.obtenerPorId(id);
        if(gerente != null) {
            return ResponseEntity.ok(assembler.toModel(gerente));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/desactivar/{id}")
    @Operation(summary = "Desactivar gerente de curso", description = "Desactiva un gerente de curso en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gerente de curso desactivado",
                    content = @Content(schema = @Schema(example = "Gerente de Curso desactivado"))),
            @ApiResponse(responseCode = "404", description = "Gerente de curso no encontrado",
                    content = @Content(schema = @Schema(example = "No se encontró el gerente")))
    })
    public ResponseEntity<String> desactivar(@PathVariable int id) {
        boolean resultado = service.desactivar(id);
        return resultado ?
                ResponseEntity.ok("Gerente de Curso desactivado") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el gerente");
    }
}