package com.example.EduTech.Assembler;

import com.example.EduTech.Controller.CursoController;
import com.example.EduTech.Controller.DocenteController;
import com.example.EduTech.Model.Usuarios.Docente;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DocenteModelAssembler implements RepresentationModelAssembler<Docente, EntityModel<Docente>> {

     /*
    RepresentionalModel = Permite a la clase contener una lista de Links de acceso
    EntityModel = Es un contenedor generico que adjunta la entidad mas una serie de enlaces
    LinkTo = Es un metodo que nos permite construir los Links o URL's de acceso que posee nuestro controlador
    */

    @Override
    public EntityModel<Docente> toModel(Docente docente) {
        return EntityModel.of(docente,
                linkTo(methodOn(DocenteController.class).obtenerPorId(docente.getId())).withSelfRel(),
                linkTo(methodOn(DocenteController.class).listar()).withRel("docentes"),
                linkTo(methodOn(DocenteController.class).crear(null)).withRel("crear"),
                linkTo(methodOn(DocenteController.class).actualizar(null)).withRel("actualizar"),
                linkTo(methodOn(DocenteController.class).eliminar(docente.getId())).withRel("eliminar")

        );
    }

    @Override
    public CollectionModel<EntityModel<Docente>> toCollectionModel(Iterable<? extends Docente> entities) {
        CollectionModel<EntityModel<Docente>> model = RepresentationModelAssembler.super.toCollectionModel(entities);
        model.add(linkTo(methodOn(DocenteController.class).listar()).withSelfRel());
        return model;
    }
}