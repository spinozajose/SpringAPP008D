package com.example.EduTech.Assembler;

import com.example.EduTech.Controller.AdminSistemaController;
import com.example.EduTech.Controller.CursoController;
import com.example.EduTech.Model.Curso.Curso;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CursoModelAssembler implements RepresentationModelAssembler<Curso, EntityModel<Curso>> {

     /*
    RepresentionalModel = Permite a la clase contener una lista de Links de acceso
    EntityModel = Es un contenedor generico que adjunta la entidad mas una serie de enlaces
    LinkTo = Es un metodo que nos permite construir los Links o URL's de acceso que posee nuestro controlador
    */

    @Override
    public EntityModel<Curso> toModel(Curso curso) {
        return EntityModel.of(curso,
                linkTo(methodOn(CursoController.class).obtenerPorId(curso.getId())).withSelfRel(),
                linkTo(methodOn(CursoController.class).listar()).withRel("cursos"),
                linkTo(methodOn(CursoController.class).crear(null)).withRel("crear"),
                linkTo(methodOn(CursoController.class).actualizar(null)).withRel("actualizar"),
                linkTo(methodOn(CursoController.class).eliminar(curso.getId())).withRel("eliminar")
        );
    }

    @Override
    public CollectionModel<EntityModel<Curso>> toCollectionModel(Iterable<? extends Curso> entities) {
        CollectionModel<EntityModel<Curso>> model = RepresentationModelAssembler.super.toCollectionModel(entities);
        model.add(linkTo(methodOn(CursoController.class).listar()).withSelfRel());
        return model;
    }
}
