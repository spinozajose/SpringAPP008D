package com.example.EduTech.Assembler;


import com.example.EduTech.Controller.CursoController;
import com.example.EduTech.Controller.EstudianteController;
import com.example.EduTech.Model.Usuarios.Estudiante;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EstudianteModelAssembler implements RepresentationModelAssembler<Estudiante, EntityModel<Estudiante>> {

    @Override
    public EntityModel<Estudiante> toModel(Estudiante estudiante) {
        return EntityModel.of(estudiante,
                linkTo(methodOn(EstudianteController.class).obtenerPorId(estudiante.getId())).withSelfRel(),
                linkTo(methodOn(EstudianteController.class).listar()).withRel("estudiantes"),
                linkTo(methodOn(EstudianteController.class).obtenerNivelAcademico(estudiante.getId())).withRel("nivel-academico"),
                linkTo(methodOn(EstudianteController.class).crear(null)).withRel("crear"),
                linkTo(methodOn(EstudianteController.class).actualizar(null)).withRel("actualizar"),
                linkTo(methodOn(EstudianteController.class).eliminar(estudiante.getId())).withRel("eliminar")
        );
    }

    @Override
    public CollectionModel<EntityModel<Estudiante>> toCollectionModel(Iterable<? extends Estudiante> entities) {
        CollectionModel<EntityModel<Estudiante>> model = RepresentationModelAssembler.super.toCollectionModel(entities);
        model.add(linkTo(methodOn(EstudianteController.class).listar()).withSelfRel());
        return model;
    }
}