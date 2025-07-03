package com.example.EduTech.Assembler;

import com.example.EduTech.Controller.EvaluacionController;
import com.example.EduTech.Model.Curso.Evaluacion;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EvaluacionModelAssembler implements RepresentationModelAssembler<Evaluacion, EntityModel<Evaluacion>> {

     /*
    RepresentionalModel = Permite a la clase contener una lista de Links de acceso
    EntityModel = Es un contenedor generico que adjunta la entidad mas una serie de enlaces
    LinkTo = Es un metodo que nos permite construir los Links o URL's de acceso que posee nuestro controlador
    */

    @Override
    public EntityModel<Evaluacion> toModel(Evaluacion evaluacion) {
        return EntityModel.of(evaluacion,
                linkTo(methodOn(EvaluacionController.class).obtenerPorId(evaluacion.getId())).withSelfRel(),
                linkTo(methodOn(EvaluacionController.class).listar()).withRel("evaluaciones"),
                linkTo(methodOn(EvaluacionController.class).eliminar(evaluacion.getId())).withRel("eliminar"),
                linkTo(methodOn(EvaluacionController.class).actualizar(evaluacion)).withRel("actualizar")
        );
    }

    @Override
    public CollectionModel<EntityModel<Evaluacion>> toCollectionModel(Iterable<? extends Evaluacion> entities) {
        CollectionModel<EntityModel<Evaluacion>> collectionModel = RepresentationModelAssembler.super.toCollectionModel(entities);

        collectionModel.add(linkTo(methodOn(EvaluacionController.class).listar()).withSelfRel());
        collectionModel.add(linkTo(methodOn(EvaluacionController.class).crear(null)).withRel("crear"));

        return collectionModel;
    }
}