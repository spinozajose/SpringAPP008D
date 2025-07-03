package com.example.EduTech.Assembler;

import com.example.EduTech.Controller.LogisticaSoporteController;
import com.example.EduTech.Model.Usuarios.LogisticaSoporte;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LogisticaSoporteModelAssembler implements RepresentationModelAssembler<LogisticaSoporte, EntityModel<LogisticaSoporte>> {

     /*
    RepresentionalModel = Permite a la clase contener una lista de Links de acceso
    EntityModel = Es un contenedor generico que adjunta la entidad mas una serie de enlaces
    LinkTo = Es un metodo que nos permite construir los Links o URL's de acceso que posee nuestro controlador
    */

    @Override
    public EntityModel<LogisticaSoporte> toModel(LogisticaSoporte logistica) {
        return EntityModel.of(logistica,
                linkTo(methodOn(LogisticaSoporteController.class).obtenerPorId(logistica.getId())).withSelfRel(),
                linkTo(methodOn(LogisticaSoporteController.class).listarTodos()).withRel("logistica-soporte"),
                linkTo(methodOn(LogisticaSoporteController.class).crear(null)).withRel("crear"),
                linkTo(methodOn(LogisticaSoporteController.class).actualizar(logistica.getId(), null)).withRel("actualizar"),
                linkTo(methodOn(LogisticaSoporteController.class).eliminar(logistica.getId())).withRel("eliminar")
        );
    }

    @Override
    public CollectionModel<EntityModel<LogisticaSoporte>> toCollectionModel(Iterable<? extends LogisticaSoporte> entities) {
        CollectionModel<EntityModel<LogisticaSoporte>> collectionModel =
                RepresentationModelAssembler.super.toCollectionModel(entities);

        collectionModel.add(linkTo(methodOn(LogisticaSoporteController.class).listarTodos()).withSelfRel());
        collectionModel.add(linkTo(methodOn(LogisticaSoporteController.class).crear(null)).withRel("crear"));

        return collectionModel;
    }
}