package com.example.EduTech.Controller;

import com.example.EduTech.Assembler.DocenteModelAssembler;
import com.example.EduTech.Model.Usuarios.Docente;
import com.example.EduTech.Service.DocenteService;
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
@RequestMapping("/docentes")
@Tag(name = "Controlador de Docentes", description = "Gestión de docentes del sistema")
public class DocenteController {

    @Autowired
    private DocenteService service;

    @Autowired
    private DocenteModelAssembler assembler;

    @PostMapping("/crear")
    @Operation(summary = "Crear docente", description = "Registra un nuevo docente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Docente creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Docente.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<EntityModel<Docente>> crear(@RequestBody Docente docente) {
        Docente nuevoDocente = service.crear(docente);
        return new ResponseEntity<>(assembler.toModel(nuevoDocente), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar")
    @Operation(summary = "Actualizar docente", description = "Modifica los datos de un docente existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Docente actualizado"),
            @ApiResponse(responseCode = "404", description = "Docente no encontrado")
    })
    public ResponseEntity<EntityModel<Docente>> actualizar(@RequestBody Docente docente) {
        Docente docenteActualizado = service.actualizar(docente);
        return ResponseEntity.ok(assembler.toModel(docenteActualizado));
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar docente", description = "Elimina un docente del sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Docente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Docente no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar docentes", description = "Obtiene todos los docentes registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de docentes obtenida"),
            @ApiResponse(responseCode = "204", description = "No hay docentes registrados")
    })
    public ResponseEntity<CollectionModel<EntityModel<Docente>>> listar() {
        List<Docente> docentes = service.listar();
        if(docentes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(assembler.toCollectionModel(docentes), HttpStatus.OK);
    }

    @GetMapping("/obtener/{id}")
    @Operation(summary = "Obtener docente por ID", description = "Recupera un docente específico por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Docente encontrado",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Docente no encontrado")
    })
    public ResponseEntity<String> obtenerPorId(@PathVariable int id) {
        String docenteInfo = service.obtenerPorId(id);
        return docenteInfo != null ?
                ResponseEntity.ok(docenteInfo) :
                ResponseEntity.notFound().build();
    }
}