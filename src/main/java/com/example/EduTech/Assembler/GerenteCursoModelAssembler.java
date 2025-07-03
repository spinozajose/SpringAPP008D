package com.example.EduTech.Assembler;

import com.example.EduTech.Controller.GerenteCursoController;
import com.example.EduTech.Model.Usuarios.GerenteCurso;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GerenteCursoModelAssembler implements RepresentationModelAssembler<GerenteCurso, EntityModel<GerenteCurso>> {

     /*
    RepresentionalModel = Permite a la clase contener una lista de Links de acceso
    EntityModel = Es un contenedor generico que adjunta la entidad mas una serie de enlaces
    LinkTo = Es un metodo que nos permite construir los Links o URL's de acceso que posee nuestro controlador
    */

    @Override
    public EntityModel<GerenteCurso> toModel(GerenteCurso gerenteCurso) {
        return EntityModel.of(gerenteCurso,
                linkTo(methodOn(GerenteCursoController.class).obtenerPorId(gerenteCurso.getId())).withSelfRel(),
                linkTo(methodOn(GerenteCursoController.class).listar()).withRel("gerentes-curso"),
                linkTo(methodOn(GerenteCursoController.class).eliminar(gerenteCurso.getId())).withRel("eliminar"),
                linkTo(methodOn(GerenteCursoController.class).actualizar(gerenteCurso)).withRel("actualizar"),
                linkTo(methodOn(GerenteCursoController.class).desactivar(gerenteCurso.getId())).withRel("desactivar")
        );
    }

    @Override
    public CollectionModel<EntityModel<GerenteCurso>> toCollectionModel(Iterable<? extends GerenteCurso> entities) {
        CollectionModel<EntityModel<GerenteCurso>> collectionModel = RepresentationModelAssembler.super.toCollectionModel(entities);

        collectionModel.add(linkTo(methodOn(GerenteCursoController.class).listar()).withSelfRel());
        collectionModel.add(linkTo(methodOn(GerenteCursoController.class).crear(null)).withRel("crear"));

        return collectionModel;
    }
}